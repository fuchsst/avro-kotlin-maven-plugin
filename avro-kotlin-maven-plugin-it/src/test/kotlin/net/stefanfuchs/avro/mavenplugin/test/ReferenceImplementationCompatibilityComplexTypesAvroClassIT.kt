package net.stefanfuchs.avro.mavenplugin.test

internal class ReferenceImplementationCompatibilityComplexTypesAvroClassIT {

/*    @Test
    fun deserializeKotlinSerializedAvroWithJavaAvro() {
        val kotlinAvro = net.stefanfuchs.avro.mavenplugin.test.kotlin.PrimitivesTypesAvroClass()
        kotlinAvro.bytesField  = "bytearray".toByteArray()
        kotlinAvro.doubleField = 345.678912
        kotlinAvro.floatField = 23.456F
        kotlinAvro.intDateField=456
        kotlinAvro.intField=123
        kotlinAvro.longTimestampField=123456789L
        kotlinAvro.optionalStringField=null
        kotlinAvro.stringField="Hello World!"

        //kotlinAvro.field4 = arrayOf(true, true, false, false, true, false, true)
        kotlinAvro.field5 = "Hello World!"
        kotlinAvro.field6 = 456
        kotlinAvro.field7 = net.stefanfuchs.avro.mavenplugin.test.kotlin.Suit.HEARTS
        kotlinAvro.field8 = "bytearray".toByteArray()
        kotlinAvro.field9 = net.stefanfuchs.avro.mavenplugin.test.kotlin.md5("0123456789abcdef".toByteArray())
        kotlinAvro.field10 = net.stefanfuchs.avro.mavenplugin.test.kotlin.TestAvroClassSubClass().apply {
            subField1 = 23.456F
            subField2 = 345.678912
        }
        kotlinAvro.field11Alias = mapOf("key1" to "mapByteArray1".toByteArray(), "key2" to "mapByteArray2".toByteArray())


        val outputStream = ByteArrayOutputStream()
        val objOutput = ObjectOutputStream(outputStream)
        kotlinAvro.writeExternal(objOutput)
        objOutput.flush()


        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val objInput = ObjectInputStream(inputStream)
        val javaAvro = net.stefanfuchs.avro.mavenplugin.test.java.PrimitivesTypesAvroClass()
        javaAvro.readExternal(objInput)

        Assertions.assertThat(javaAvro.getField1()).isEqualTo(kotlinAvro.field1)
        Assertions.assertThat(javaAvro.getField2()).isEqualTo(kotlinAvro.field2)
        Assertions.assertThat(javaAvro.getField3()).isEqualTo(kotlinAvro.field3)
        //Assertions.assertThat(javaAvro.getField4()).isEqualTo(kotlinAvro.field4)
        Assertions.assertThat(javaAvro.getField5()).isEqualTo(kotlinAvro.field5)
        Assertions.assertThat(javaAvro.getField6()).isEqualTo(kotlinAvro.field6)
        Assertions.assertThat(javaAvro.getField7()).isEqualTo(kotlinAvro.field7)
        Assertions.assertThat(javaAvro.getField8()).isEqualTo(kotlinAvro.field8)
        Assertions.assertThat(javaAvro.getField9()).isEqualTo(kotlinAvro.field9)
        Assertions.assertThat(javaAvro.getField10()).isEqualTo(kotlinAvro.field10)
        Assertions.assertThat(javaAvro.getField11()).isEqualTo(kotlinAvro.field11)
    }

    @Test
    fun deserializeJavaSerializedAvroWithKotlinAvro() {
        val javaAvro = net.stefanfuchs.avro.mavenplugin.test.java.PrimitivesTypesAvroClass()
        javaAvro.setField1(123)
        javaAvro.setField2(4567890123L)
        javaAvro.setField3(mapOf("key1" to "value1", "key2" to "value2", "key3" to "value3"))
        //javaAvro.setField4(mutableListOf(true, true, false, false, true, false, true))
        javaAvro.setField5("Hello World!")
        javaAvro.setField6(456)
        javaAvro.setField7(net.stefanfuchs.avro.mavenplugin.test.java.Suit.HEARTS)
        javaAvro.setField8(ByteBuffer.wrap("bytearray".toByteArray()))
        javaAvro.setField9(net.stefanfuchs.avro.mavenplugin.test.java.md5("0123456789abcdef".toByteArray()))
        javaAvro.setField10(net.stefanfuchs.avro.mavenplugin.test.java.TestAvroClassSubClass().apply {
            setSubField1(23.456F)
            setSubField2(345.678912)
        })
        javaAvro.setField11(mapOf("key1" to ByteBuffer.wrap("mapByteArray1".toByteArray()),
                "key2" to ByteBuffer.wrap("mapByteArray2".toByteArray()))
        )

        val outputStream = ByteArrayOutputStream()
        val objOutput = ObjectOutputStream(outputStream)
        javaAvro.writeExternal(objOutput)
        objOutput.flush()


        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val objInput = ObjectInputStream(inputStream)
        val kotlinAvro = net.stefanfuchs.avro.mavenplugin.test.kotlin.PrimitivesTypesAvroClass()
        kotlinAvro.readExternal(objInput)

        Assertions.assertThat(javaAvro.getField1()).isEqualTo(kotlinAvro.field1)
        Assertions.assertThat(javaAvro.getField2()).isEqualTo(kotlinAvro.field2)
        Assertions.assertThat(javaAvro.getField3()).isEqualTo(kotlinAvro.field3)
        //Assertions.assertThat(javaAvro.getField4()).isEqualTo(kotlinAvro.field4)
        Assertions.assertThat(javaAvro.getField5()).isEqualTo(kotlinAvro.field5)
        Assertions.assertThat(javaAvro.getField6()).isEqualTo(kotlinAvro.field6)
        Assertions.assertThat(javaAvro.getField7()).isEqualTo(kotlinAvro.field7)
        Assertions.assertThat(javaAvro.getField8()).isEqualTo(kotlinAvro.field8)
        Assertions.assertThat(javaAvro.getField9()).isEqualTo(kotlinAvro.field9)
        Assertions.assertThat(javaAvro.getField10()).isEqualTo(kotlinAvro.field10)
        Assertions.assertThat(javaAvro.getField11()).isEqualTo(kotlinAvro.field11)
    }*/
}
