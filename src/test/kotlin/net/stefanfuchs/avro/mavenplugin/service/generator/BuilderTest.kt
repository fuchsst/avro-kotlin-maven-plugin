package net.stefanfuchs.avro.mavenplugin.service.generator

import org.apache.avro.Schema
import org.junit.jupiter.api.Test

internal class BuilderTest {

    @Test
    fun build() {
        val schemaFile = BuilderTest::class.java.getResource("/avro/complete_schema.avsc")
        val schema = Schema.Parser().parse(schemaFile.openStream())
        println(schema)
        println()
        println()

        val schemaSourceCodes = Builder()
                .readSchema(BuilderTest::class.java.getResource("/avro/complete_schema.avsc").openStream())
                .buildList()
        schemaSourceCodes.forEach { (key, value) -> println(value) }


    }
}
