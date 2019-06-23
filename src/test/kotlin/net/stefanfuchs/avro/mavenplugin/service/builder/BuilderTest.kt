package net.stefanfuchs.avro.mavenplugin.service.builder

import org.apache.avro.Schema
import org.junit.jupiter.api.Test

internal class BuilderTest {

    @Test
    fun build() {
        val schemaFile = BuilderTest::class.java.getResource("/avro/complete_schema.avsc")
        val schema = Schema.Parser().parse(schemaFile.openStream())
        val schemaSourceCodes = Builder()
                .readSchema(schemaFile.openStream())
                .buildComplexBuilderList()


        println(schema)
        println()
        println()

        schemaSourceCodes.forEach { println(it.filename) }
        println()
        println()

        schemaSourceCodes.forEach { println(it.build()) }

    }
}
