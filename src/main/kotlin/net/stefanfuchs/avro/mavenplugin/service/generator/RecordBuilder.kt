package net.stefanfuchs.avro.mavenplugin.service.generator

import org.apache.avro.Schema

class RecordBuilder(val schema: Schema, val schemaRepository: MutableList<Schema>) {
    val packageName: String = schema.namespace
    val className: String = schema.name
    val fullname: String = "${schema.namespace}.${schema.name}"

    init {
        require(schema.type == Schema.Type.RECORD)
//        require(schemaRepository.none { "${it.namespace}.${it.name}" == fullname })
        schemaRepository.add(schema)
    }


    val doc: String = """
        /**
        ${schema.doc}
        **/
        """


    val code: String  by lazy {
        """
        package ${packageName}

        ${if (schema.doc?.isNotEmpty() == true) doc else ""}
        @org.apache.avro.specific.AvroGenerated
        data class ${className}(
           ${schema.fields.map { it.asConstructorVarString() }}
        ) : org.apache.avro.specific.SpecificRecordBase(), org.apache.avro.specific.SpecificRecord {

            ${schema.fields.map { it.asAliasGeterSetter() }}

            companion object {
                private const val serialVersionUID = ${schema.hashCode()}L
                private val model = org.apache.avro.specific.SpecificData()

                val classSchema: org.apache.avro.Schema = org.apache.avro.Schema.Parser().parse("${schema}")

                /**
                 * Return the BinaryMessageEncoder instance used by this class.
                 * @return the message encoder used by this class
                 */
                val encoder = org.apache.avro.message.BinaryMessageEncoder<${className}>(model, classSchema)

                /**
                 * Return the BinaryMessageDecoder instance used by this class.
                 * @return the message decoder used by this class
                 */
                val decoder = org.apache.avro.message.BinaryMessageDecoder<${className}>(model, classSchema)

                /**
                 * Create a new BinaryMessageDecoder instance for this class that uses the specified [SchemaStore].
                 * @param resolver a [SchemaStore] used to find schemas by fingerprint
                 * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
                 */
                fun createDecoder(resolver: org.apache.avro.message.SchemaStore): org.apache.avro.message.BinaryMessageDecoder<${className}> {
                    return org.apache.avro.message.BinaryMessageDecoder(model, classSchema, resolver)
                }

                /**
                 * Deserializes a ${className} from a ByteBuffer.
                 * @param b a byte buffer holding serialized data for an instance of this class
                 * @return a ${className} instance decoded from the given buffer
                 * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
                 */
                @Throws(java.io.IOException::class)
                fun fromByteBuffer(b: java.nio.ByteBuffer): ${className} {
                    return decoder.decode(b)
                }


                private val `WRITER\$` = model.createDatumWriter(classSchema) as org.apache.avro.io.DatumWriter<${className}>

                private val `READER\$` = model.createDatumReader(classSchema) as org.apache.avro.io.DatumReader<${className}>
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
                `WRITER\$`.write(this, org.apache.avro.specific.SpecificData.getEncoder(objectOutput))
            }

            @Throws(java.io.IOException::class)
            override fun readExternal(objectInput: java.io.ObjectInput) {
                `READER\$`.read(this, org.apache.avro.specific.SpecificData.getDecoder(objectInput))
            }

            override fun hasCustomCoders(): Boolean {
                return true
            }

            // Used by DatumWriter.  Applications should not call.
            override fun get(`field$`: Int): Any? {
                return when (`field$`) {
                    ${schema.fields.map { it.asGetIndexFieldMapping() }}
                    else -> throw org.apache.avro.AvroRuntimeException("Bad index")
                }
            }

            // Used by DatumReader.  Applications should not call.
            override fun put(`field$\`: Int, `value$`: Any) {
                when (`field$`) {
                    ${schema.fields.map { it.asPutIndexFieldMapping() }}
                    else -> throw org.apache.avro.AvroRuntimeException("Bad index")
                }
            }

            @Throws(java.io.IOException::class)
            override fun customEncode(out: org.apache.avro.io.Encoder) {
               ${schema.fields.map { it.asCustomEncoderPart() }}
            }


            @Throws(java.io.IOException::class)
            override fun customDecode(input: org.apache.avro.io.ResolvingDecoder) {
                val fieldOrder = input.readFieldOrderIfDiff()
                if (fieldOrder == null) {
                    ${schema.fields.map { it.asCustomDecoderPart() }}

                } else {
                    for (i in 0..${schema.fields.size - 1}) {
                        when (fieldOrder[i].pos()) {
                            ${schema.fields.map { it.asCustomDecoderPart() }}
                            else -> throw java.io.IOException("Corrupt ResolvingDecoder.")
                        }
                    }
                }
            }
        }
    """.trimIndent()
    }
}
