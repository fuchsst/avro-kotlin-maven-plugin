package net.stefanfuchs.avro.mavenplugin.service.generator

import org.apache.avro.Schema
import java.io.File
import java.io.InputStream

class Builder {

    private val schemas: MutableList<Schema> = mutableListOf()

    fun readSchemas(path: String, extensionFilter: List<String> = listOf(".avsc", ".avro")): Builder {
        val url = Builder::class.java.getResource(path)
        File(url!!.path)
                .walkTopDown()
                .filter { it.isFile && it.extension in extensionFilter }
                .forEach { readSchema(it.inputStream()) }
        return this
    }


    fun readSchema(inputStream: InputStream): Builder {
        readSchema(Schema.Parser().parse(inputStream))
        return this
    }

    fun readSchema(schema: Schema): Builder {
        findAllRecordAndEnumSchemas(schema)
                .forEach { schemas.add(it) }
        return this
    }

    private fun findAllRecordAndEnumSchemas(schema: Schema): List<Schema> {
        return if (schema.type == Schema.Type.RECORD) {
            listOf(schema) +
                    schema
                            .fields
                            .flatMap { findAllRecordAndEnumSchemas(it.schema()) }
        } else if (schema.type == Schema.Type.ENUM) {
            listOf(schema)
        } else if (schema.type == Schema.Type.ARRAY) {
            findAllRecordAndEnumSchemas(schema.elementType)
        } else if (schema.type == Schema.Type.MAP) {
            findAllRecordAndEnumSchemas(schema.valueType)
        } else if (schema.type == Schema.Type.UNION) {
            schema
                    .types
                    .flatMap { findAllRecordAndEnumSchemas(it) }
        } else {
            emptyList()
        }
    }

    fun buildList(): Map<String, String> {
        return schemas
                .map {
                    "${it.namespace}.${it.name}" to
                            when (it.type) {
                                Schema.Type.RECORD -> RecordBuilder(it).build()
                                Schema.Type.ENUM -> EnumBuilder(it).build()
                                else -> throw IllegalArgumentException("Schema $it is not of expected type Record or Enum")
                            }
                }
                .toMap()
    }


}
