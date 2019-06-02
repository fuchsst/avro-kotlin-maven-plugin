package net.stefanfuchs.avro.mavenplugin.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AvroSchemaItemPrimitiveTest {


    @Test
    fun `Primitive toString is enclosed with "`() {
        val given = "int"
        val actual = AvroSchemaItem.AvroSchemaItemPrimitive(given)
        Assertions.assertEquals(""""int"""", actual.toString())
    }


    @Test
    fun `AvroSchemaItemPrimitive returns rawString enclosed in " if not a known primitive type`() {
        val given = "Test.Class"
        val actual = AvroSchemaItem.AvroSchemaItemPrimitive(given)
        Assertions.assertEquals(""""${actual.rawString}"""", actual.toString())
    }
}
