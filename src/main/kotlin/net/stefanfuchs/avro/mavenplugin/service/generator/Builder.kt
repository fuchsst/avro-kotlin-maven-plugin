package net.stefanfuchs.avro.mavenplugin.service.generator

import org.apache.avro.Schema
import java.io.File
import java.io.InputStream

class Builder {

    val schemas: MutableList<Schema> = mutableListOf()

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
        RecordBuilder(schema, schemas)
        return this
    }

    fun build(): Map<String, String> = schemas
            .map { "${it.namespace}.${it.name}" to RecordBuilder(it, schemas).code }
            .toMap()


}
