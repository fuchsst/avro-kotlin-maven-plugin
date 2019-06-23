package net.stefanfuchs.avro.mavenplugin.service.builder

import net.stefanfuchs.avro.mavenplugin.service.builder.complex.ComplexBuilder
import net.stefanfuchs.avro.mavenplugin.service.builder.complex.EnumBuilder
import net.stefanfuchs.avro.mavenplugin.service.builder.complex.FixedBuilder
import net.stefanfuchs.avro.mavenplugin.service.builder.complex.RecordBuilder
import org.apache.avro.Schema
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream

class Builder {

    private val schemas: MutableSet<Schema> = mutableSetOf()
    private val logger = LoggerFactory.getLogger(Builder::class.java)

    fun readSchemas(path: String, extensionFilter: List<String> = listOf(".avsc", ".avro")): Builder {
        logger.info("Read schemas from '$path' with extension filter $extensionFilter")

        File(path)
                .walkTopDown()
                .filter { it.isFile }
                .forEach {
                    logger.info("Reading file ${it.absolutePath}")
                    readSchema(it.inputStream())
                }
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
        } else if (schema.type == Schema.Type.FIXED) {
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

    fun buildComplexBuilderList(): Set<ComplexBuilder> {
        val complexBuilders = schemas
                .map {
                    when (it.type) {
                        Schema.Type.RECORD -> RecordBuilder(it)
                        Schema.Type.ENUM -> EnumBuilder(it)
                        Schema.Type.FIXED -> FixedBuilder(it)
                        else -> throw IllegalArgumentException("Schema $it is not of expected type Record, Enum or Fixed")
                    }
                }
                .toSet()
        logger.info("Found ${complexBuilders.size} schema that will generate the following Kotlin files:")
        complexBuilders.forEach { logger.info(it.filename) }
        return complexBuilders
    }


}
