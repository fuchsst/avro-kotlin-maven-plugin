package net.stefanfuchs.avro.mavenplugin.service

import com.google.gson.JsonParser
import net.stefanfuchs.avro.mavenplugin.model.input.FieldType
import net.stefanfuchs.avro.mavenplugin.model.input.LogicalFieldType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AvroSchemaItemLogicTypeTest {


    @Test
    fun `AvroSchemaItemLogicTypeBytesDecimal parses all attributes`() {
        val given = JsonParser().parse(
                """{
                      "type" : "bytes",
                      "logicalType" : "decimal",
                      "precision": 16,
                      "scale": 4
        }""".trimIndent()).asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemLogicTypeBytesDecimal(given)
        Assertions.assertEquals(FieldType.BYTES, actual.fieldType)
        Assertions.assertEquals(LogicalFieldType.DECIMAL, actual.logicalType)
        Assertions.assertEquals(16, actual.precision)
        Assertions.assertEquals(4, actual.scale)
    }

    @Test
    fun `AvroSchemaItemLogicTypeStringUuid parses all attributes`() {
        val given = JsonParser().parse(
                """{
                      "type" : "string",
                      "logicalType" : "uuid"
        }""".trimIndent()).asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemLogicTypeStringUuid(given)
        Assertions.assertEquals(FieldType.STRING, actual.fieldType)
        Assertions.assertEquals(LogicalFieldType.UUID, actual.logicalType)
    }

    @Test
    fun `AvroSchemaItemLogicTypeIntDate parses all attributes`() {
        val given = JsonParser().parse(
                """{
                      "type" : "int",
                      "logicalType" : "date"
        }""".trimIndent()).asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemLogicTypeIntDate(given)
        Assertions.assertEquals(FieldType.INT, actual.fieldType)
        Assertions.assertEquals(LogicalFieldType.DATE, actual.logicalType)
    }

    @Test
    fun `AvroSchemaItemLogicTypeIntTimeMillis parses all attributes`() {
        val given = JsonParser().parse(
                """{
                      "type" : "int",
                      "logicalType" : "time-millis"
        }""".trimIndent()).asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemLogicTypeIntTimeMillis(given)
        Assertions.assertEquals(FieldType.INT, actual.fieldType)
        Assertions.assertEquals(LogicalFieldType.TIME_MILLISECONDS, actual.logicalType)
    }

    @Test
    fun `AvroSchemaItemLogicTypeIntTimeMicros parses all attributes`() {
        val given = JsonParser().parse(
                """{
                      "type" : "int",
                      "logicalType" : "time-micros"
        }""".trimIndent()).asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemLogicTypeIntTimeMicros(given)
        Assertions.assertEquals(FieldType.INT, actual.fieldType)
        Assertions.assertEquals(LogicalFieldType.TIME_MICROSECONDS, actual.logicalType)
    }

    @Test
    fun `AvroSchemaItemLogicTypeIntTimestampMillis parses all attributes`() {
        val given = JsonParser().parse(
                """{
                      "type" : "int",
                      "logicalType" : "timestamp-millis"
        }""".trimIndent()).asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemLogicTypeIntTimestampMillis(given)
        Assertions.assertEquals(FieldType.INT, actual.fieldType)
        Assertions.assertEquals(LogicalFieldType.TIMESTAMP_MILLISECONDS, actual.logicalType)
    }

    @Test
    fun `AvroSchemaItemLogicTypeIntTimestampMicros parses all attributes`() {
        val given = JsonParser().parse(
                """{
                      "type" : "int",
                      "logicalType" : "timestamp-micros"
        }""".trimIndent()).asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemLogicTypeIntTimestampMicros(given)
        Assertions.assertEquals(FieldType.INT, actual.fieldType)
        Assertions.assertEquals(LogicalFieldType.TIMESTAMP_MICROSECONDS, actual.logicalType)
    }

    @Test
    fun `AvroSchemaItemLogicTypeFixedDuration parses all attributes`() {
        val given = JsonParser().parse(
                """{
                      "name" : "FixedDurationType",
                      "size" : 12,
                      "type" : "fixed",
                      "logicalType" : "duration"
        }""".trimIndent()).asJsonObject
        val actual = AvroSchemaItem.AvroSchemaItemLogicTypeFixedDuration(given)
        Assertions.assertEquals(FieldType.INT, actual.fieldType)
        Assertions.assertEquals(LogicalFieldType.TIMESTAMP_MICROSECONDS, actual.logicalType)
        Assertions.assertEquals("FixedDurationType", actual.name)
        Assertions.assertEquals(12, actual.size)
    }

}
