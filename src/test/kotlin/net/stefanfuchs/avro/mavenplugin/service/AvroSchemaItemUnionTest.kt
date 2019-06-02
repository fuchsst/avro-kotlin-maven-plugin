package net.stefanfuchs.avro.mavenplugin.service

import com.google.gson.JsonParser
import net.stefanfuchs.avro.mavenplugin.model.input.FieldType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AvroSchemaItemUnionTest {

    @Test
    fun `NamedAvroSchemaItem parse converts JsonArray to union`() {
        val given = JsonParser().parse("""[ "null", "int" ]""").asJsonArray
        val actual = AvroSchemaItem.AvroSchemaItemUnion(given)
        assertTrue(actual.isNullable)
        Assertions.assertEquals(FieldType.INT, actual.types!![0]!!.fieldType)
    }
}
