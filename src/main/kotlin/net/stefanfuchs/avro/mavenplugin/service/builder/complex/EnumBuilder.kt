package net.stefanfuchs.avro.mavenplugin.service.builder.complex

import org.apache.avro.Schema

class EnumBuilder(val schema: Schema) : ComplexBuilder {
    override val packageName: String = schema.namespace
    override val className: String = schema.name
    override val filepath: String = "/${schema.namespace.replace(".", "/")}"
    override val filename: String = "${schema.name}.kt"
    override val fullFilename: String = "$filepath/$filename"

    init {
        require(schema.type == Schema.Type.ENUM)
    }

    private val doc: String = """
        /**
        ${schema.doc}
        **/
        """.trimIndent()


    override fun build(): String {
        return """
        package ${packageName}

        ${if (schema.doc?.isNotEmpty() == true) doc else ""}
        @org.apache.avro.specific.AvroGenerated
        enum class ${className} : org.apache.avro.generic.GenericEnumSymbol<${className}> {
            ${getEnumSymbols()};

            override fun getSchema(): org.apache.avro.Schema {
                return classSchema
            }

            companion object {
                private const val serialVersionUID = ${schema.hashCode()}L
                val classSchema: org.apache.avro.Schema =  org.apache.avro.Schema.Parser().parse("${schema.toString().replace("\"", "\\\"")}");
            }
        }
        """.trimIndent()
    }

    private fun getEnumSymbols(): String {
        return schema.enumSymbols.joinToString(", ")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EnumBuilder

        if (schema != other.schema) return false

        return true
    }

    override fun hashCode(): Int {
        return schema.hashCode()
    }


}
