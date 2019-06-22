package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

object UnionFieldBuilder : FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field): String {
        require(field.schema().type == Schema.Type.UNION)
        return "null"
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String): String {
        require(schema.type == Schema.Type.UNION)
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

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.UNION)
        return """if (input.readIndex() != 1) {
                                    input.readNull()
                                    null
                                } else {
                                    ${schema.types[1].asCustomDecoderPartKotlinCodeString()}
                                }
                             """
    }

    override fun toFieldTypeKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.UNION)
        return if (schema.types.size == 2 && schema.types[0].type == Schema.Type.NULL)
            "${schema.types[1].asFieldTypeKotlinCodeString()}?"
        else
            "Any?"
    }
}
