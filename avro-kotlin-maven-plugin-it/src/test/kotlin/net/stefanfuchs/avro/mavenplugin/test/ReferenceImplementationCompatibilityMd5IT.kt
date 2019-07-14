package net.stefanfuchs.avro.mavenplugin.test

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

internal class ReferenceImplementationCompatibilityMd5IT {

    @Test
    fun deserializeKotlinSerializedAvroWithJavaAvro() {
        val kotlinAvro = net.stefanfuchs.avro.mavenplugin.test.kotlin.Md5()
        kotlinAvro.bytes("0123456789abcdef".toByteArray())

        val outputStream = ByteArrayOutputStream()
        val objOutput = ObjectOutputStream(outputStream)
        kotlinAvro.writeExternal(objOutput)
        objOutput.flush()


        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val objInput = ObjectInputStream(inputStream)
        val javaAvro = net.stefanfuchs.avro.mavenplugin.test.java.Md5()
        javaAvro.readExternal(objInput)

        Assertions.assertThat(javaAvro.bytes()).isEqualTo(kotlinAvro.bytes())
    }

    @Test
    fun deserializeJavaSerializedAvroWithKotlinAvro() {
        val javaAvro = net.stefanfuchs.avro.mavenplugin.test.java.Md5()
        javaAvro.bytes("0123456789abcdef".toByteArray())

        val outputStream = ByteArrayOutputStream()
        val objOutput = ObjectOutputStream(outputStream)
        javaAvro.writeExternal(objOutput)
        objOutput.flush()


        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val objInput = ObjectInputStream(inputStream)
        val kotlinAvro = net.stefanfuchs.avro.mavenplugin.test.kotlin.Md5()
        kotlinAvro.readExternal(objInput)

        Assertions.assertThat(kotlinAvro.bytes()).isEqualTo(javaAvro.bytes())
    }
}
