package net.stefanfuchs.avro.mavenplugin.service.generator

import org.apache.avro.Schema

class Builder(val schema: Schema) {
    private val schemas:List<Schema> = listOf(schema)



    fun build():String {
        TODO()
    }
}
