package net.stefanfuchs.avro.mavenplugin.service.builder.types.complex

import org.apache.avro.Schema

internal class FixedBuilder(val schema: Schema) : ComplexBuilder {
    override val packageName: String = schema.namespace
    override val className: String = schema.name
    override val filepath: String = "/${schema.namespace.replace(".", "/")}"
    override val filename: String = "${schema.name}.kt"
    override val fullFilename: String = "$filepath/$filename"

    init {
        require(schema.type == Schema.Type.FIXED)
    }

    private val doc: String = """
        /**
        ${schema.doc}
        **/
        """.trimIndent()

    //TODO replace code with code for fixed
    override fun build(): String {
        return """
        package $packageName

        ${if (schema.doc?.isNotEmpty() == true) doc else ""}
        @org.apache.avro.specific.FixedSize(${schema.fixedSize})
        @org.apache.avro.specific.AvroGenerated
        class $className : org.apache.avro.specific.SpecificFixed {

            constructor() : super()

            /**
             * Creates a new $className with the given bytes.
             * @param bytes The bytes to create the new $className.
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
                private const val serialVersionUID = ${schema.hashCode()}L
                val classSchema: org.apache.avro.Schema =  org.apache.avro.Schema.Parser().parse("${schema.toString().replace("\"", "\\\"")}");

                private val `WRITER$` = org.apache.avro.specific.SpecificDatumWriter<$className>(classSchema)

                private val `READER$` = org.apache.avro.specific.SpecificDatumReader<$className>(classSchema)
            }
        }
        """.trimIndent()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FixedBuilder

        if (schema != other.schema) return false

        return true
    }

    override fun hashCode(): Int {
        return schema.hashCode()
    }


}
