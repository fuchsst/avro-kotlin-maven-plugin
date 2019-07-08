package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

internal object IntFieldBuilder : FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field): String {
        require(field.schema().type == Schema.Type.INT)
        return "0"
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String): String {
        require(schema.type == Schema.Type.INT)
        return "output.writeInt($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.INT)
        return "input.readInt()"
    }

    override fun toFieldTypeKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.INT)
        return "Int"
    }
}
