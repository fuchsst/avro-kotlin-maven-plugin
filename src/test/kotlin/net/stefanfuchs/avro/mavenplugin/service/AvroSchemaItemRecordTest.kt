package net.stefanfuchs.avro.mavenplugin.service

import com.google.gson.JsonParser
import net.stefanfuchs.avro.mavenplugin.model.FieldType
import net.stefanfuchs.avro.mavenplugin.model.LocicalFieldType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertNotNull


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AvroSchemaItemRecordTest {

    @Test
    fun `NamedAvroSchemaItem TYPE returns AvroSchemaItem when present`() {
        val given = JsonParser().parse("""{ "type": "long" }""").asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).type

        assert(actual is AvroSchemaItem.AvroSchemaItemPrimitive)

        val expected = AvroSchemaItem.AvroSchemaItemPrimitive("long")
        assertEquals(expected, actual)
    }

    @Test
    fun `NamedAvroSchemaItem TYPE returns NULL when not present`() {
        val given = JsonParser().parse("""{  }""").asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).type

        assertNull(actual)
    }

    @Test
    fun `NamedAvroSchemaItem NAME returns String when present`() {
        val given = JsonParser().parse("""{ "name": "Test" }""").asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).name

        val expected = "Test"
        assertEquals(expected, actual)
    }

    @Test
    fun `NamedAvroSchemaItem NAME returns NULL when not present`() {
        val given = JsonParser().parse("""{  }""").asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).name

        assertNull(actual)
    }

    @Test
    fun `NamedAvroSchemaItem DOC returns String when present`() {
        val given = JsonParser().parse("""{ "doc": "Test\nSecond Line" }""").asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).doc

        val expected = "Test\nSecond Line"
        assertEquals(expected, actual)
    }

    @Test
    fun `NamedAvroSchemaItem DOC returns NULL when not present`() {
        val given = JsonParser().parse("""{  }""").asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).doc

        assertNull(actual)
    }

    @Test
    fun `NamedAvroSchemaItem ALIASES returns StringList when present`() {
        val given = JsonParser().parse("""{ "aliases": ["Test", "Second Line"] }""").asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).aliases

        val expected = listOf("Test", "Second Line")
        assertEquals(expected, actual)
    }

    @Test
    fun `NamedAvroSchemaItem ALIASES returns NULL when not present`() {
        val given = JsonParser().parse("""{  }""").asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).aliases

        assertNull(actual)
    }

    @Test
    fun `NamedAvroSchemaItem NAMESPACE returns String when present`() {
        val given = JsonParser().parse("""{ "namespace": "Test.Namespace" }""").asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).namespace

        val expected = "Test.Namespace"
        assertEquals(expected, actual)
    }

    @Test
    fun `NamedAvroSchemaItem NAMESPACE returns NULL when not present`() {
        val given = JsonParser().parse("""{  }""").asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).aliases

        assertNull(actual)
    }

    @Test
    fun `NamedAvroSchemaItem FIELDS returns primitive FieldsList when present`() {
        val given = JsonParser().parse("""{ "fields": [{ "name" : "TestField", "type": "long"}, { "name" : "TestField2", "type": "string"}  ] }""").asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).fields

        assertNotNull(actual)
        assert(actual[0].type is AvroSchemaItem.AvroSchemaItemPrimitive)
        assert(actual[1].type is AvroSchemaItem.AvroSchemaItemPrimitive)
        assertEquals("TestField", actual[0].name)
        assertEquals(FieldType.LONG, (actual[0].type as AvroSchemaItem.AvroSchemaItemPrimitive).fieldType)
        assertEquals("TestField2", actual[1].name)
        assertEquals(FieldType.STRING, (actual[1].type as AvroSchemaItem.AvroSchemaItemPrimitive).fieldType)
    }

    @Test
    fun `NamedAvroSchemaItem FIELDS returns complex FieldsList when present`() {
        val given = JsonParser().parse("""
            |{ "name" : "TestRecordType",
            |  "type" : "record",
            |  "fields": [
            |             { "name" : "TestField",
            |               "type": "bytes",
            |               "logicalType" : "decimal",
            |               "precision" : 12
            |             },
            |             { "name" : "TestField2",
            |               "type": {
            |                         "type" : "record",
            |                         "name" : "TestField2.ExampleType",
            |                         "namespace" : "test.namespace",
            |                         "fields" : [ { "name" : "ExampleTypeField", "type" : "int" } ]
            |                       }
            |             }
            |            ]
            |}""".trimMargin()).asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).fields

        assertNotNull(actual)
        val testField = actual[0]
        val testField2 = actual[1]
        val testField2SubType = actual[1].type as AvroSchemaItem.AvroSchemaItemRecord

        assertEquals("TestField", testField.name)
        assertEquals(FieldType.BYTES, (testField.type as AvroSchemaItem.AvroSchemaItemPrimitive).fieldType)
        assertEquals(LocicalFieldType.DECIMAL, testField.logicalType)
        assertEquals("TestField2", testField2.name)

        assertEquals(FieldType.RECORD, (testField2SubType.type as AvroSchemaItem.AvroSchemaItemPrimitive).fieldType)
        assertEquals("TestField2.ExampleType", testField2SubType.name)
        assertEquals("test.namespace", testField2SubType.namespace)
        assertEquals("ExampleTypeField", testField2SubType.fields?.get(0)?.name)
        assertEquals(FieldType.INT, (testField2SubType.fields?.get(0)?.type as AvroSchemaItem.AvroSchemaItemPrimitive).fieldType)
    }

    @Test
    fun `NamedAvroSchemaItem FIELDS returns NULL when not present`() {
        val given = JsonParser().parse("""{  }""").asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemRecord(given).fields

        assertNull(actual)
    }
}
