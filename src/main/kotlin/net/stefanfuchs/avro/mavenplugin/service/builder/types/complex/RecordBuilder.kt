package net.stefanfuchs.avro.mavenplugin.service.builder.types.complex

import net.stefanfuchs.avro.mavenplugin.service.builder.fields.asAliasGetterSetterKotlinCodeString
import net.stefanfuchs.avro.mavenplugin.service.builder.fields.asConstructorVarKotlinCodeString
import net.stefanfuchs.avro.mavenplugin.service.builder.fields.asCustomDecoderPartKotlinCodeString
import net.stefanfuchs.avro.mavenplugin.service.builder.fields.asCustomEncoderPartKotlinCodeString
import net.stefanfuchs.avro.mavenplugin.service.builder.fields.asGetIndexFieldMappingKotlinCodeString
import net.stefanfuchs.avro.mavenplugin.service.builder.fields.asPutIndexFieldMappingKotlinCodeString
import org.apache.avro.Schema

internal class RecordBuilder(val schema: Schema) : ComplexBuilder {
    override val packageName: String = schema.namespace
    override val className: String = schema.name
    override val filepath: String = "/${schema.namespace.replace(".", "/")}"
    override val filename: String = "${schema.name}.kt"
    override val fullFilename: String = "$filepath/$filename"

    init {
        require(schema.type == Schema.Type.RECORD)
//        require(schemaRepository.none { "${it.namespace}.${it.name}" == fullname })
    }


    private val doc: String = """
        /**
        ${schema.doc}
        **/
        """

    //TODO add logical types getter/setter
    //TODO make fields private and use specific getter/setter
    //TODO add builder functions for each field


    override fun build(): String {
        return """
        package $packageName

        ${if (schema.doc?.isNotEmpty() == true) doc else ""}
        @org.apache.avro.specific.AvroGenerated
        @Suppress("UNCHECKED_CAST")
        data class $className(
            ${getConstructorFieldsCodeString()}
        ) : org.apache.avro.specific.SpecificRecordBase(), org.apache.avro.specific.SpecificRecord {

            ${getAliasesCodeString()}

            companion object {
                private const val serialVersionUID = ${schema.hashCode()}L
                private val model = org.apache.avro.specific.SpecificData()

                val classSchema: org.apache.avro.Schema = org.apache.avro.Schema.Parser().parse("${getSchemaCodeString()}")

                /**
                 * Return the BinaryMessageEncoder instance used by this class.
                 * @return the message encoder used by this class
                 */
                val encoder = org.apache.avro.message.BinaryMessageEncoder<$className>(model, classSchema)

                /**
                 * Return the BinaryMessageDecoder instance used by this class.
                 * @return the message decoder used by this class
                 */
                val decoder = org.apache.avro.message.BinaryMessageDecoder<$className>(model, classSchema)

                /**
                 * Create a new BinaryMessageDecoder instance for this class that uses the specified [org.apache.avro.message.SchemaStore].
                 * @param resolver a [org.apache.avro.message.SchemaStore] used to find schemas by fingerprint
                 * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
                 */
                fun createDecoder(resolver: org.apache.avro.message.SchemaStore): org.apache.avro.message.BinaryMessageDecoder<$className> {
                    return org.apache.avro.message.BinaryMessageDecoder(model, classSchema, resolver)
                }

                /**
                 * Deserializes a $className from a ByteBuffer.
                 * @param b a byte buffer holding serialized data for an instance of this class
                 * @return a $className instance decoded from the given buffer
                 * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
                 */
                @Throws(java.io.IOException::class)
                fun fromByteBuffer(b: java.nio.ByteBuffer): $className {
                    return decoder.decode(b)
                }


                private val `WRITER$` = model.createDatumWriter(classSchema) as org.apache.avro.io.DatumWriter<$className>

                private val `READER$` = model.createDatumReader(classSchema) as org.apache.avro.io.DatumReader<$className>
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
                    ${getGetFieldsCodeString()}
                    else -> throw org.apache.avro.AvroRuntimeException("Bad index")
                }
            }

            // Used by DatumReader.  Applications should not call.
            override fun put(`field$`: Int, 
                             `value$`: Any?) {
                when (`field$`) {
                    ${getPutFieldsCodeString()}
                    else -> throw org.apache.avro.AvroRuntimeException("Bad index")
                }
            }

            @Throws(java.io.IOException::class)
            override fun customEncode(output: org.apache.avro.io.Encoder) {
                ${getCustomEncoderFieldsCodeString()}
            }


            @Throws(java.io.IOException::class)
            override fun customDecode(input: org.apache.avro.io.ResolvingDecoder) {
                val fieldOrder = input.readFieldOrderIfDiff()
                if (fieldOrder == null) {
                    ${getCustomDecoderFieldsCodeString()}
                } else {
                    for (i in 0..${schema.fields.size - 1}) {
                        when (fieldOrder[i].pos()) {
                            ${getCustomDecoderFieldsByOrderCodeString()}
                            else -> throw java.io.IOException("Corrupt ResolvingDecoder.")
                        }
                    }
                }
            }
        }
        """.trimIndent()
    }

    private fun getSchemaCodeString() = schema.toString().replace("\"", "\\\"")

    private fun getConstructorFieldsCodeString() = schema
            .fields
            .joinToString(",\n" + indendSpaces(3)) { it.asConstructorVarKotlinCodeString() }

    private fun getAliasesCodeString() = schema
            .fields
            .filter { it.aliases().isNotEmpty() }
            .flatMap { it.asAliasGetterSetterKotlinCodeString()!!.split("\n") }
            .joinToString("\n" + indendSpaces(3))

    private fun getGetFieldsCodeString() = schema
            .fields
            .joinToString("\n" + indendSpaces(5)) { it.asGetIndexFieldMappingKotlinCodeString() }

    private fun getPutFieldsCodeString() = schema
            .fields
            .joinToString("\n" + indendSpaces(5)) { it.asPutIndexFieldMappingKotlinCodeString() }

    private fun getCustomEncoderFieldsCodeString() = schema
            .fields
            .joinToString("\n" + indendSpaces(4)) {
                it.asCustomEncoderPartKotlinCodeString().split("\n").joinToString("\n" + indendSpaces(4))
            }

    private fun getCustomDecoderFieldsCodeString() = schema
            .fields
            .joinToString("\n" + indendSpaces(5)) {
                "this.${it.name()} = ${it.asCustomDecoderPartKotlinCodeString().split("\n").joinToString("\n" + indendSpaces(4))}"
            }

    private fun getCustomDecoderFieldsByOrderCodeString() = schema
            .fields
            .joinToString("\n" + indendSpaces(7)) {
                "${it.pos()} -> this.${it.name()} = ${it.asCustomDecoderPartKotlinCodeString()}"
            }

    private fun indendSpaces(level: Int): String {
        val spacesPerLevel = 4
        return " ".repeat(level * spacesPerLevel)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecordBuilder

        if (schema != other.schema) return false

        return true
    }

    override fun hashCode(): Int {
        return schema.hashCode()
    }


}
