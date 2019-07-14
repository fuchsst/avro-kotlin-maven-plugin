package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class UnionTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return """
                                if ($fieldName == null) {
                                  output.writeIndex(0)
                                  output.writeNull()
                                } else {
                                  output.writeIndex(1)
                                  ${schema.types[1].asCustomEncoderPartKotlinCodeString(fieldName)}
                                }
        """.trimIndent()
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return """if (input.readIndex() != 1) {
                                    input.readNull()
                                    null
                                } else {
                                    ${schema.types[1].asCustomDecoderPartKotlinCodeString()}
                                }
                             """.trimIndent()
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return if (schema.types.size == 2 && schema.types[0].type == Schema.Type.NULL)
            "${schema.types[1].asFieldTypeKotlinCodeString()}?"
        else
            "Any?"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return if (schema.types.size == 2 && schema.types[0].type == Schema.Type.NULL)
            "${schema.types[1].asFieldTypeKotlinCodeString()}?"
        else
            "Any?"
    }
}
