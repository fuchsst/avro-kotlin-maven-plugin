package net.stefanfuchs.avro.mavenplugin.service.generator

import org.apache.avro.Schema
import org.junit.jupiter.api.Test

internal class BuilderTest {

    @Test
    fun build() {
        val schemaFile = BuilderTest::class.java.getResource("/avro/complete_schema.avsc")
        val schema = Schema.Parser().parse(schemaFile.openStream())
        val schemaSourceCodes = Builder()
                .readSchema(BuilderTest::class.java.getResource("/avro/complete_schema.avsc").openStream())
                .buildList()


        println(schema)
        println()
        println()

        schemaSourceCodes.forEach { (key, value) -> println(key) }
        println()
        println()

        schemaSourceCodes.forEach { (key, value) -> println(value) }

    }
}
