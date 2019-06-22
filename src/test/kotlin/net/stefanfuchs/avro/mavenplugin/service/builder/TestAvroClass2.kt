package net.stefanfuchs.avro.mavenplugin.test.pkg


@org.apache.avro.specific.AvroGenerated
class TestAvroClass(
        var field1: Int = -1,
        var field2: Long = 0,
        var field3: Map<String, String> = mutableMapOf(),
        var field4: Array<Boolean> = emptyArray(),
        var field5: String? = null,
        var field6: Int = 0,
        var field7: net.stefanfuchs.avro.mavenplugin.test.pkg.Suit? = null,
        var field8: ByteArray = ByteArray(0),
        var field9: net.stefanfuchs.avro.mavenplugin.test.pkg.md5 = net.stefanfuchs.avro.mavenplugin.test.pkg.md5(),
        var field10: net.stefanfuchs.avro.mavenplugin.test.pkg.TestAvroClassSubClass = net.stefanfuchs.avro.mavenplugin.test.pkg.TestAvroClassSubClass(),
        var field11: Map<String, ByteArray> = mutableMapOf()
) : org.apache.avro.specific.SpecificRecordBase(), org.apache.avro.specific.SpecificRecord {

    var field4Alias: Array<Boolean>
        get() = this.field4
        set(value) {
            this.field4 = value
        }
    var field11Alias: Map<String, ByteArray>
        get() = this.field11
        set(value) {
            this.field11 = value
        }

    companion object {
        private const val serialVersionUID = -126203350L
        private val model = org.apache.avro.specific.SpecificData()

        val classSchema: org.apache.avro.Schema = org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestAvroClass\",\"namespace\":\"net.stefanfuchs.avro.mavenplugin.test.pkg\",\"fields\":[{\"name\":\"field1\",\"type\":\"int\",\"default\":-1},{\"name\":\"field2\",\"type\":\"long\",\"default\":0,\"logicalType\":\"timestamp-millis\"},{\"name\":\"field3\",\"type\":{\"type\":\"map\",\"values\":\"string\"}},{\"name\":\"field4\",\"type\":{\"type\":\"array\",\"items\":\"boolean\"},\"aliases\":[\"field4Alias\"]},{\"name\":\"field5\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"field6\",\"type\":\"int\",\"logicalType\":\"date\"},{\"name\":\"field7\",\"type\":[\"null\",{\"type\":\"enum\",\"name\":\"Suit\",\"symbols\":[\"SPADES\",\"HEARTS\",\"DIAMONDS\",\"CLUBS\"]}]},{\"name\":\"field8\",\"type\":\"bytes\"},{\"name\":\"field9\",\"type\":{\"type\":\"fixed\",\"name\":\"md5\",\"size\":16}},{\"name\":\"field10\",\"type\":{\"type\":\"record\",\"name\":\"TestAvroClassSubClass\",\"fields\":[{\"name\":\"subField1\",\"type\":\"float\"},{\"name\":\"subField2\",\"type\":\"double\"}]}},{\"name\":\"field11\",\"type\":{\"type\":\"map\",\"values\":\"bytes\"},\"aliases\":[\"field11Alias\"]}]}")

        /**
         * Return the BinaryMessageEncoder instance used by this class.
         * @return the message encoder used by this class
         */
        val encoder = org.apache.avro.message.BinaryMessageEncoder<TestAvroClass>(model, classSchema)

        /**
         * Return the BinaryMessageDecoder instance used by this class.
         * @return the message decoder used by this class
         */
        val decoder = org.apache.avro.message.BinaryMessageDecoder<TestAvroClass>(model, classSchema)

        /**
         * Create a new BinaryMessageDecoder instance for this class that uses the specified [org.apache.avro.message.SchemaStore].
         * @param resolver a [org.apache.avro.message.SchemaStore] used to find schemas by fingerprint
         * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
         */
        fun createDecoder(resolver: org.apache.avro.message.SchemaStore): org.apache.avro.message.BinaryMessageDecoder<TestAvroClass> {
            return org.apache.avro.message.BinaryMessageDecoder(model, classSchema, resolver)
        }

        /**
         * Deserializes a TestAvroClass from a ByteBuffer.
         * @param b a byte buffer holding serialized data for an instance of this class
         * @return a TestAvroClass instance decoded from the given buffer
         * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
         */
        @Throws(java.io.IOException::class)
        fun fromByteBuffer(b: java.nio.ByteBuffer): TestAvroClass {
            return decoder.decode(b)
        }


        private val `WRITER$` = model.createDatumWriter(classSchema) as org.apache.avro.io.DatumWriter<TestAvroClass>

        private val `READER$` = model.createDatumReader(classSchema) as org.apache.avro.io.DatumReader<TestAvroClass>
    }

    /**
     * Serializes this TestAvroClass to a ByteBuffer.
     * @return a buffer holding the serialized data for this instance
     * @throws java.io.IOException if this instance could not be serialized
     */
    @Throws(java.io.IOException::class)
    fun toByteBuffer(): java.nio.ByteBuffer {
        return encoder.encode(this)
    }

    override fun getSpecificData(): org.apache.avro.specific.SpecificData {
        return model
    }

    override fun getSchema(): org.apache.avro.Schema {
        return classSchema
    }

    @Throws(java.io.IOException::class)
    override fun writeExternal(objectOutput: java.io.ObjectOutput) {
        `WRITER$`.write(this, org.apache.avro.specific.SpecificData.getEncoder(objectOutput))
    }

    @Throws(java.io.IOException::class)
    override fun readExternal(objectInput: java.io.ObjectInput) {
        `READER$`.read(this, org.apache.avro.specific.SpecificData.getDecoder(objectInput))
    }

    override fun hasCustomCoders(): Boolean {
        return true
    }

    // Used by DatumWriter.  Applications should not call.
    override fun get(`field$`: Int): Any? {
        return when (`field$`) {
            0 -> this.field1
            1 -> this.field2
            2 -> this.field3
            3 -> this.field4
            4 -> this.field5
            5 -> this.field6
            6 -> this.field7
            7 -> this.field8
            8 -> this.field9
            9 -> this.field10
            10 -> this.field11
            else -> throw org.apache.avro.AvroRuntimeException("Bad index")
        }
    }

    // Used by DatumReader.  Applications should not call.
    override fun put(`field$`: Int,
                     `value$`: Any) {
        when (`field$`) {
            0 -> this.field1 = `value$` as Int
            1 -> this.field2 = `value$` as Long
            2 -> this.field3 = `value$` as Map<String, String>
            3 -> this.field4 = `value$` as Array<Boolean>
            4 -> this.field5 = `value$` as String?
            5 -> this.field6 = `value$` as Int
            6 -> this.field7 = `value$` as net.stefanfuchs.avro.mavenplugin.test.pkg.Suit?
            7 -> this.field8 = `value$` as ByteArray
            8 -> this.field9 = `value$` as net.stefanfuchs.avro.mavenplugin.test.pkg.md5
            9 -> this.field10 = `value$` as net.stefanfuchs.avro.mavenplugin.test.pkg.TestAvroClassSubClass
            10 -> this.field11 = `value$` as Map<String, ByteArray>
            else -> throw org.apache.avro.AvroRuntimeException("Bad index")
        }
    }

    @Throws(java.io.IOException::class)
    override fun customEncode(output: org.apache.avro.io.Encoder) {
        output.writeInt(this.field1)
        output.writeLong(this.field2)
        run {
            val mapSize = this.field3.size.toLong()
            output.writeMapStart()
            output.setItemCount(mapSize)
            var actualMapSize: Long = 0
            for ((key, value) in this.field3) {
                actualMapSize++
                output.startItem()
                output.writeString(key)
                output.writeString(value)
            }
            output.writeMapEnd()
            if (actualMapSize != mapSize)
                throw java.util.ConcurrentModificationException("Map-size written was $mapSize, but element count was $actualMapSize.")
        }
        run {
            val arraySize = this.field4.size.toLong()
            output.writeArrayStart()
            output.setItemCount(arraySize)
            var actualArraySize: Long = 0
            this.field4.forEach {
                actualArraySize++
                output.startItem()
                output.writeBoolean(it)
            }
            output.writeArrayEnd()
            if (actualArraySize != arraySize)
                throw java.util.ConcurrentModificationException("Array-size written was $arraySize, but element count was $actualArraySize.")
        }
        if (this.field5 == null) {
            output.writeIndex(0)
            output.writeNull()
        } else {
            output.writeIndex(1)
            output.writeString(this.field5)
        }
        output.writeInt(this.field6)
        if (this.field7 == null) {
            output.writeIndex(0)
            output.writeNull()
        } else {
            output.writeIndex(1)
            output.writeEnum(this.field7!!.ordinal)
        }
        output.writeBytes(this.field8)
        output.writeFixed(this.field9.bytes(), 0, 16)
        this.field10.customEncode(output)
        run {
            val mapSize = this.field11.size.toLong()
            output.writeMapStart()
            output.setItemCount(mapSize)
            var actualMapSize: Long = 0
            for ((key, value) in this.field11) {
                actualMapSize++
                output.startItem()
                output.writeString(key)
                output.writeBytes(value)
            }
            output.writeMapEnd()
            if (actualMapSize != mapSize)
                throw java.util.ConcurrentModificationException("Map-size written was $mapSize, but element count was $actualMapSize.")
        }
    }


    @Throws(java.io.IOException::class)
    override fun customDecode(input: org.apache.avro.io.ResolvingDecoder) {
        val fieldOrder = input.readFieldOrderIfDiff()
        if (fieldOrder == null) {
            this.field1 = input.readInt()
            this.field2 = input.readLong()
            this.field3 = mutableMapOf<String, String>().apply {
                var size = input.readMapStart()
                while (0 < size) {
                    while (size != 0L) {
                        val key = input.readString()
                        val value = input.readString()
                        this[key] = value
                        size--
                    }
                    size = input.mapNext()
                }
            }
            this.field4 = mutableListOf<Boolean>().apply {
                var size = input.readArrayStart()
                while (size > 0) {
                    while (size != 0L) {
                        this.add(input.readBoolean())
                        size--
                    }
                    size = input.arrayNext()
                }
            }.toTypedArray()
            this.field5 = if (input.readIndex() != 1) {
                input.readNull()
                null
            } else {
                input.readString()
            }

            this.field6 = input.readInt()
            this.field7 = if (input.readIndex() != 1) {
                input.readNull()
                null
            } else {
                net.stefanfuchs.avro.mavenplugin.test.pkg.Suit.values()[input.readEnum()]
            }

            this.field8 = input.readBytes(null).array()
            this.field9 = { val bytes = ByteArray(16); input.readFixed(bytes, 0, 16); net.stefanfuchs.avro.mavenplugin.test.pkg.md5(bytes) }.invoke()
            this.field10 = net.stefanfuchs.avro.mavenplugin.test.pkg.TestAvroClassSubClass().apply { customDecode(input) }
            this.field11 = mutableMapOf<String, ByteArray>().apply {
                var size = input.readMapStart()
                while (0 < size) {
                    while (size != 0L) {
                        val key = input.readString()
                        val value = input.readBytes(null).array()
                        this[key] = value
                        size--
                    }
                    size = input.mapNext()
                }
            }
        } else {
            for (i in 0..10) {
                when (fieldOrder[i].pos()) {
                    0 -> this.field1 = input.readInt()
                    1 -> this.field2 = input.readLong()
                    2 -> this.field3 = mutableMapOf<String, String>().apply {
                        var size = input.readMapStart()
                        while (0 < size) {
                            while (size != 0L) {
                                val key = input.readString()
                                val value = input.readString()
                                this[key] = value
                                size--
                            }
                            size = input.mapNext()
                        }
                    }
                    3 -> this.field4 = mutableListOf<Boolean>().apply {
                        var size = input.readArrayStart()
                        while (size > 0) {
                            while (size != 0L) {
                                this.add(input.readBoolean())
                                size--
                            }
                            size = input.arrayNext()
                        }
                    }.toTypedArray()
                    4 -> this.field5 = if (input.readIndex() != 1) {
                        input.readNull()
                        null
                    } else {
                        input.readString()
                    }

                    5 -> this.field6 = input.readInt()
                    6 -> this.field7 = if (input.readIndex() != 1) {
                        input.readNull()
                        null
                    } else {
                        net.stefanfuchs.avro.mavenplugin.test.pkg.Suit.values()[input.readEnum()]
                    }

                    7 -> this.field8 = input.readBytes(null).array()
                    8 -> this.field9 = { val bytes = ByteArray(16); input.readFixed(bytes, 0, 16); net.stefanfuchs.avro.mavenplugin.test.pkg.md5(bytes) }.invoke()
                    9 -> this.field10 = net.stefanfuchs.avro.mavenplugin.test.pkg.TestAvroClassSubClass().apply { customDecode(input) }
                    10 -> this.field11 = mutableMapOf<String, ByteArray>().apply {
                        var size = input.readMapStart()
                        while (0 < size) {
                            while (size != 0L) {
                                val key = input.readString()
                                val value = input.readBytes(null).array()
                                this[key] = value
                                size--
                            }
                            size = input.mapNext()
                        }
                    }
                    else -> throw java.io.IOException("Corrupt ResolvingDecoder.")
                }
            }
        }
    }
}


@org.apache.avro.specific.AvroGenerated
enum class Suit : org.apache.avro.generic.GenericEnumSymbol<Suit> {
    SPADES, HEARTS, DIAMONDS, CLUBS;

    override fun getSchema(): org.apache.avro.Schema {
        return classSchema
    }

    companion object {
        private const val serialVersionUID = -1787655751L
        val classSchema: org.apache.avro.Schema = org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"Suit\",\"namespace\":\"net.stefanfuchs.avro.mavenplugin.test.pkg\",\"symbols\":[\"SPADES\",\"HEARTS\",\"DIAMONDS\",\"CLUBS\"]}");
    }
}


@org.apache.avro.specific.FixedSize(16)
@org.apache.avro.specific.AvroGenerated
class md5 : org.apache.avro.specific.SpecificFixed {

    constructor() : super()

    /**
     * Creates a new md5 with the given bytes.
     * @param bytes The bytes to create the new md5.
     */
    constructor(bytes: ByteArray) : super(bytes)

    @Throws(java.io.IOException::class)
    override fun writeExternal(objOutput: java.io.ObjectOutput) {
        `WRITER$`.write(this, org.apache.avro.specific.SpecificData.getEncoder(objOutput))
    }

    @Throws(java.io.IOException::class)
    override fun readExternal(objInput: java.io.ObjectInput) {
        `READER$`.read(this, org.apache.avro.specific.SpecificData.getDecoder(objInput))
    }


    override fun getSchema(): org.apache.avro.Schema {
        return classSchema
    }

    companion object {
        private const val serialVersionUID = 175943878L
        val classSchema: org.apache.avro.Schema = org.apache.avro.Schema.Parser().parse("{\"type\":\"fixed\",\"name\":\"md5\",\"namespace\":\"net.stefanfuchs.avro.mavenplugin.test.pkg\",\"size\":16}");

        private val `WRITER$` = org.apache.avro.specific.SpecificDatumWriter<md5>(classSchema)

        private val `READER$` = org.apache.avro.specific.SpecificDatumReader<md5>(classSchema)
    }
}


@org.apache.avro.specific.AvroGenerated
class TestAvroClassSubClass(
        var subField1: Float = 0F,
        var subField2: Double = 0.0
) : org.apache.avro.specific.SpecificRecordBase(), org.apache.avro.specific.SpecificRecord {


    companion object {
        private const val serialVersionUID = 1027214422L
        private val model = org.apache.avro.specific.SpecificData()

        val classSchema: org.apache.avro.Schema = org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestAvroClassSubClass\",\"namespace\":\"net.stefanfuchs.avro.mavenplugin.test.pkg\",\"fields\":[{\"name\":\"subField1\",\"type\":\"float\"},{\"name\":\"subField2\",\"type\":\"double\"}]}")

        /**
         * Return the BinaryMessageEncoder instance used by this class.
         * @return the message encoder used by this class
         */
        val encoder = org.apache.avro.message.BinaryMessageEncoder<TestAvroClassSubClass>(model, classSchema)

        /**
         * Return the BinaryMessageDecoder instance used by this class.
         * @return the message decoder used by this class
         */
        val decoder = org.apache.avro.message.BinaryMessageDecoder<TestAvroClassSubClass>(model, classSchema)

        /**
         * Create a new BinaryMessageDecoder instance for this class that uses the specified [org.apache.avro.message.SchemaStore].
         * @param resolver a [org.apache.avro.message.SchemaStore] used to find schemas by fingerprint
         * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
         */
        fun createDecoder(resolver: org.apache.avro.message.SchemaStore): org.apache.avro.message.BinaryMessageDecoder<TestAvroClassSubClass> {
            return org.apache.avro.message.BinaryMessageDecoder(model, classSchema, resolver)
        }

        /**
         * Deserializes a TestAvroClassSubClass from a ByteBuffer.
         * @param b a byte buffer holding serialized data for an instance of this class
         * @return a TestAvroClassSubClass instance decoded from the given buffer
         * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
         */
        @Throws(java.io.IOException::class)
        fun fromByteBuffer(b: java.nio.ByteBuffer): TestAvroClassSubClass {
            return decoder.decode(b)
        }


        private val `WRITER$` = model.createDatumWriter(classSchema) as org.apache.avro.io.DatumWriter<TestAvroClassSubClass>

        private val `READER$` = model.createDatumReader(classSchema) as org.apache.avro.io.DatumReader<TestAvroClassSubClass>
    }

    /**
     * Serializes this TestAvroClass to a ByteBuffer.
     * @return a buffer holding the serialized data for this instance
     * @throws java.io.IOException if this instance could not be serialized
     */
    @Throws(java.io.IOException::class)
    fun toByteBuffer(): java.nio.ByteBuffer {
        return encoder.encode(this)
    }

    override fun getSpecificData(): org.apache.avro.specific.SpecificData {
        return model
    }

    override fun getSchema(): org.apache.avro.Schema {
        return classSchema
    }

    @Throws(java.io.IOException::class)
    override fun writeExternal(objectOutput: java.io.ObjectOutput) {
        `WRITER$`.write(this, org.apache.avro.specific.SpecificData.getEncoder(objectOutput))
    }

    @Throws(java.io.IOException::class)
    override fun readExternal(objectInput: java.io.ObjectInput) {
        `READER$`.read(this, org.apache.avro.specific.SpecificData.getDecoder(objectInput))
    }

    override fun hasCustomCoders(): Boolean {
        return true
    }

    // Used by DatumWriter.  Applications should not call.
    override fun get(`field$`: Int): Any? {
        return when (`field$`) {
            0 -> this.subField1
            1 -> this.subField2
            else -> throw org.apache.avro.AvroRuntimeException("Bad index")
        }
    }

    // Used by DatumReader.  Applications should not call.
    override fun put(`field$`: Int,
                     `value$`: Any) {
        when (`field$`) {
            0 -> this.subField1 = `value$` as Float
            1 -> this.subField2 = `value$` as Double
            else -> throw org.apache.avro.AvroRuntimeException("Bad index")
        }
    }

    @Throws(java.io.IOException::class)
    override fun customEncode(output: org.apache.avro.io.Encoder) {
        output.writeFloat(this.subField1)
        output.writeDouble(this.subField2)
    }


    @Throws(java.io.IOException::class)
    override fun customDecode(input: org.apache.avro.io.ResolvingDecoder) {
        val fieldOrder = input.readFieldOrderIfDiff()
        if (fieldOrder == null) {
            this.subField1 = input.readFloat()
            this.subField2 = input.readDouble()
        } else {
            for (i in 0..1) {
                when (fieldOrder[i].pos()) {
                    0 -> this.subField1 = input.readFloat()
                    1 -> this.subField2 = input.readDouble()
                    else -> throw java.io.IOException("Corrupt ResolvingDecoder.")
                }
            }
        }
    }
}
