package net.stefanfuchs.avro.mavenplugin.service.generator

import org.apache.avro.Schema

class EnumBuilder(val schema: Schema) {
    val packageName: String = schema.namespace
    val className: String = schema.name
    val fullname: String = "${schema.namespace}.${schema.name}"

    init {
        require(schema.type == Schema.Type.ENUM)
    }

    val doc: String = """
        /**
        ${schema.doc}
        **/
        """.trimIndent()


    fun build(): String {
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

}
