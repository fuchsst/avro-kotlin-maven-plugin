package net.stefanfuchs.avro.mavenplugin.test

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

internal class ReferenceImplementationCompatibilityPrimitivesTypesAvroClassIT {

    @Test
    fun deserializeKotlinSerializedAvroWithJavaAvro() {
        val kotlinAvro = net.stefanfuchs.avro.mavenplugin.test.kotlin.PrimitivesTypesAvroClass()

        kotlinAvro.doubleField = 345.678912
        kotlinAvro.floatField = 23.456F
        kotlinAvro.intDateField = 456
        kotlinAvro.intField = 123
        kotlinAvro.longTimestampField = 123456789L
        kotlinAvro.optionalStringField = null
        kotlinAvro.stringField = "Hello World!"

        val outputStream = ByteArrayOutputStream()
        val objOutput = ObjectOutputStream(outputStream)
        kotlinAvro.writeExternal(objOutput)
        objOutput.flush()


        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val objInput = ObjectInputStream(inputStream)
        val javaAvro = net.stefanfuchs.avro.mavenplugin.test.java.PrimitivesTypesAvroClass()
        javaAvro.readExternal(objInput)

        Assertions.assertThat(javaAvro.getDoubleField()).isEqualTo(kotlinAvro.doubleField)
        Assertions.assertThat(javaAvro.getFloatField()).isEqualTo(kotlinAvro.floatField)
        Assertions.assertThat(javaAvro.getIntDateField()).isEqualTo(kotlinAvro.intDateField)
        Assertions.assertThat(javaAvro.getIntField()).isEqualTo(kotlinAvro.intField)
        Assertions.assertThat(javaAvro.getLongTimestampField()).isEqualTo(kotlinAvro.longTimestampField)
        Assertions.assertThat(javaAvro.getOptionalStringField()).isNull()
        Assertions.assertThat(javaAvro.getStringField()).isEqualTo(kotlinAvro.stringField)
    }

    @Test
    fun deserializeJavaSerializedAvroWithKotlinAvro() {
        val javaAvro = net.stefanfuchs.avro.mavenplugin.test.java.PrimitivesTypesAvroClass()
        javaAvro.setDoubleField(345.678912)
        javaAvro.setFloatField(23.456F)
        javaAvro.setIntDateField(456)
        javaAvro.setIntField(123)
        javaAvro.setLongTimestampField(123456789L)
        javaAvro.setOptionalStringField(null)
        javaAvro.setStringField("Hello World!")

        val outputStream = ByteArrayOutputStream()
        val objOutput = ObjectOutputStream(outputStream)
        javaAvro.writeExternal(objOutput)
        objOutput.flush()


        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val objInput = ObjectInputStream(inputStream)
        val kotlinAvro = net.stefanfuchs.avro.mavenplugin.test.kotlin.PrimitivesTypesAvroClass()
        kotlinAvro.readExternal(objInput)

        Assertions.assertThat(javaAvro.getDoubleField()).isEqualTo(kotlinAvro.doubleField)
        Assertions.assertThat(javaAvro.getFloatField()).isEqualTo(kotlinAvro.floatField)
        Assertions.assertThat(javaAvro.getIntDateField()).isEqualTo(kotlinAvro.intDateField)
        Assertions.assertThat(javaAvro.getIntField()).isEqualTo(kotlinAvro.intField)
        Assertions.assertThat(javaAvro.getLongTimestampField()).isEqualTo(kotlinAvro.longTimestampField)
        Assertions.assertThat(javaAvro.getOptionalStringField()).isNull()
        Assertions.assertThat(javaAvro.getStringField()).isEqualTo(kotlinAvro.stringField)
    }
}
