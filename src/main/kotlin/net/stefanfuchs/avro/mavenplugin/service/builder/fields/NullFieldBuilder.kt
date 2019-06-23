package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

object NullFieldBuilder : FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field): String {
        require(field.schema().type == Schema.Type.NULL)
        return "Any?"
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String): String {
        require(schema.type == Schema.Type.NULL)
        return "output.writeNull()"
    }

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.NULL)
        return "Any?"
    }

    override fun toFieldTypeKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.NULL)
        return "Any?"
    }
}
