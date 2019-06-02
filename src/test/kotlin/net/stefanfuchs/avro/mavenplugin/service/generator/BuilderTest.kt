package net.stefanfuchs.avro.mavenplugin.service.generator

import org.apache.avro.Schema
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BuilderTest {

    @Test
    fun build() {
       val schemaFile= BuilderTest::class.java.getResource("/avro/complete_schema.avsc")
        val schema= Schema.Parser().parse(schemaFile.openStream())
        print(schema)
    }
}
