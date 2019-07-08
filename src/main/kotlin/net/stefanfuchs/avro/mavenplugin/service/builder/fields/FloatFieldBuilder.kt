package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

internal object FloatFieldBuilder : FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field): String {
        require(field.schema().type == Schema.Type.FLOAT)
        return "0F"
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String): String {
        require(schema.type == Schema.Type.FLOAT)
        return "output.writeFloat($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.FLOAT)
        return "input.readFloat()"
    }

    override fun toFieldTypeKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.FLOAT)
        return "Float"
    }
}
