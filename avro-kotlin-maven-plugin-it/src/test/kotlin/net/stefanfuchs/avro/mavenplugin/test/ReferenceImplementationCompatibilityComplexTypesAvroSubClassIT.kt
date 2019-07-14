package net.stefanfuchs.avro.mavenplugin.test

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

internal class ReferenceImplementationCompatibilityComplexTypesAvroSubClassIT {

    @Test
    fun deserializeKotlinSerializedAvroWithJavaAvro() {
        val kotlinAvro = net.stefanfuchs.avro.mavenplugin.test.kotlin.ComplexTypesAvroSubClass()
        kotlinAvro.subField1 = 123.45F
        kotlinAvro.subField2 = 2345.6789

        val outputStream = ByteArrayOutputStream()
        val objOutput = ObjectOutputStream(outputStream)
        kotlinAvro.writeExternal(objOutput)
        objOutput.flush()


        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val objInput = ObjectInputStream(inputStream)
        val javaAvro = net.stefanfuchs.avro.mavenplugin.test.java.ComplexTypesAvroSubClass()
        javaAvro.readExternal(objInput)

        Assertions.assertThat(javaAvro.getSubField1()).isCloseTo(kotlinAvro.subField1, Assertions.offset(0.0001F))
        Assertions.assertThat(javaAvro.getSubField2()).isCloseTo(kotlinAvro.subField2, Assertions.offset(0.000001))
    }

    @Test
    fun deserializeJavaSerializedAvroWithKotlinAvro() {
        val javaAvro = net.stefanfuchs.avro.mavenplugin.test.java.ComplexTypesAvroSubClass()
        javaAvro.setSubField1(123.45F)
        javaAvro.setSubField2(2345.6789)

        val outputStream = ByteArrayOutputStream()
        val objOutput = ObjectOutputStream(outputStream)
        javaAvro.writeExternal(objOutput)
        objOutput.flush()


        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val objInput = ObjectInputStream(inputStream)
        val kotlinAvro = net.stefanfuchs.avro.mavenplugin.test.kotlin.ComplexTypesAvroSubClass()
        kotlinAvro.readExternal(objInput)

        Assertions.assertThat(kotlinAvro.subField1).isCloseTo(javaAvro.getSubField1(), Assertions.offset(0.0001F))
        Assertions.assertThat(kotlinAvro.subField2).isCloseTo(javaAvro.getSubField2(), Assertions.offset(0.000001))
    }
}
