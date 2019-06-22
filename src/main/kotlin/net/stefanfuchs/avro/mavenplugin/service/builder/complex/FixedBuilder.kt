package net.stefanfuchs.avro.mavenplugin.service.builder.complex

import org.apache.avro.Schema

class FixedBuilder(val schema: Schema) {
    val packageName: String = schema.namespace
    val className: String = schema.name
    val fullname: String = "${schema.namespace}.${schema.name}"

    init {
        require(schema.type == Schema.Type.FIXED)
    }

    val doc: String = """
        /**
        ${schema.doc}
        **/
        """.trimIndent()

    //TODO replace code with code for fixed
    fun build(): String {
        return """
        package ${packageName}

        ${if (schema.doc?.isNotEmpty() == true) doc else ""}
        @org.apache.avro.specific.FixedSize(${schema.fixedSize})
        @org.apache.avro.specific.AvroGenerated
        class ${className} : org.apache.avro.specific.SpecificFixed {

            constructor() : super()

            /**
             * Creates a new ${className} with the given bytes.
             * @param bytes The bytes to create the new ${className}.
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

                private val `WRITER$` = org.apache.avro.specific.SpecificDatumWriter<${className}>(classSchema)

                private val `READER$` = org.apache.avro.specific.SpecificDatumReader<${className}>(classSchema)
            }
        }
        """.trimIndent()
    }

    private fun getEnumSymbols(): String {
        return schema.enumSymbols.joinToString(", ")
    }

}
