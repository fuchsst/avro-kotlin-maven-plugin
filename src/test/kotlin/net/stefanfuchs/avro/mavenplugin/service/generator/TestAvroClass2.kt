package net.stefanfuchs.avro.mavenplugin.service.generator


@org.apache.avro.specific.AvroGenerated
data class TestAvroClass(
        /**
         * Gets the value of the 'Field1' field.
         * @return The value of the 'Field1' field.
         */
        /**
         * Sets the value of the 'Field1' field.
         * @param value the value to set.
         */
        var Field1: Int = 0,
        /**
         * Gets the value of the 'Field2' field.
         * @return The value of the 'Field2' field.
         */
        /**
         * Sets the value of the 'Field2' field.
         * @param value the value to set.
         */

        var Field2: Long = 0,
        /**
         * Gets the value of the 'Field3' field.
         * @return The value of the 'Field3' field.
         */
        /**
         * Sets the value of the 'Field3' field.
         * @param value the value to set.
         */
        var Field3: MutableMap<String, String> = mutableMapOf(),
        /**
         * Gets the value of the 'Field4' field.
         * @return The value of the 'Field4' field.
         */
        /**
         * Sets the value of the 'Field4' field.
         * @param value the value to set.
         */
        var Field4: String? = null
) : org.apache.avro.specific.SpecificRecordBase(), org.apache.avro.specific.SpecificRecord {

    var Field1Alias: Int
        get() = this.Field1
        set(value) {
            this.Field1 = value
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

    // Used by DatumWriter.  Applications should not call.
    override fun get(`field$`: Int): Any? {
        return when (`field$`) {
            0 -> this.Field1
            1 -> this.Field2
            2 -> this.Field3
            3 -> this.Field4
            else -> throw org.apache.avro.AvroRuntimeException("Bad index")
        }
    }

    // Used by DatumReader.  Applications should not call.
    override fun put(`field$`: Int, `value$`: Any) {
        when (`field$`) {
            0 -> this.Field1 = `value$` as Int
            1 -> this.Field2 = `value$` as Long
            2 -> this.Field3 = `value$` as MutableMap<String, String>
            3 -> this.Field4 = `value$` as String
            else -> throw org.apache.avro.AvroRuntimeException("Bad index")
        }
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

    @Throws(java.io.IOException::class)
    override fun customEncode(out: org.apache.avro.io.Encoder) {
        out.writeInt(this.Field1)

        out.writeLong(this.Field2)

        val size0 = this.Field3.size.toLong()
        out.writeMapStart()
        out.setItemCount(size0)
        var actualSize0: Long = 0
        for ((key, v0) in this.Field3) {
            actualSize0++
            out.startItem()
            out.writeString(key)
            out.writeString(v0)
        }
        out.writeMapEnd()
        if (actualSize0 != size0)
            throw java.util.ConcurrentModificationException("Map-size written was $size0, but element count was $actualSize0.")

        if (this.Field4 == null) {
            out.writeIndex(0)
            out.writeNull()
        } else {
            out.writeIndex(1)
            out.writeString(this.Field4!!)
        }

    }

    @Throws(java.io.IOException::class)
    override fun customDecode(input: org.apache.avro.io.ResolvingDecoder) {
        val fieldOrder = input.readFieldOrderIfDiff()
        if (fieldOrder == null) {
            this.Field1 = input.readInt()

            this.Field2 = input.readLong()

            var size0 = input.readMapStart()
            var m0: MutableMap<String, String>? = this.Field3 // Need fresh name due to limitation of macro system
            if (m0 == null) {
                m0 = HashMap(size0.toInt())
                this.Field3 = m0
            } else
                m0.clear()
            while (0 < size0) {
                while (size0 != 0L) {
                    val k0: String = input.readString()
                    val v0: String = input.readString()
                    m0[k0] = v0
                    size0--
                }
                size0 = input.mapNext()
            }

            if (input.readIndex() != 1) {
                input.readNull()
                this.Field4 = null
            } else {
                this.Field4 = input.readString()
            }

        } else {
            for (i in 0..3) {
                when (fieldOrder[i].pos()) {
                    0 -> this.Field1 = input.readInt()

                    1 -> this.Field2 = input.readLong()

                    2 -> {
                        var size0 = input.readMapStart()
                        var m0: MutableMap<String, String>? = this.Field3 // Need fresh name due to limitation of macro system
                        if (m0 == null) {
                            m0 = java.util.HashMap(size0.toInt())
                            this.Field3 = m0
                        } else
                            m0.clear()
                        while (0 < size0) {
                            while (size0 != 0L) {
                                val k0 = input.readString()
                                val v0 = input.readString()
                                m0[k0] = v0
                                size0--
                            }
                            size0 = input.mapNext()
                        }
                    }

                    3 -> if (input.readIndex() != 1) {
                        input.readNull()
                        this.Field4 = null
                    } else {
                        this.Field4 = input.readString()
                    }

                    else -> throw java.io.IOException("Corrupt ResolvingDecoder.")
                }
            }
        }
    }

    companion object {
        private const val serialVersionUID = 9212763865403403724L
        private val model = org.apache.avro.specific.SpecificData()

        val classSchema: org.apache.avro.Schema = org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TestAvroClass\",\"namespace\":\"net.stefanfuchs.avro.mavenplugin.test.pkg\",\"fields\":[{\"name\":\"Field1\",\"type\":\"int\"},{\"name\":\"Field2\",\"type\":\"long\",\"logicalType\":\"timestamp-millis\"},{\"name\":\"Field3\",\"type\":{\"type\":\"map\",\"values\":\"string\"}},{\"name\":\"Field4\",\"type\":[\"null\",\"string\"]}]}")

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
         * Create a new BinaryMessageDecoder instance for this class that uses the specified [SchemaStore].
         * @param resolver a [SchemaStore] used to find schemas by fingerprint
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
}
