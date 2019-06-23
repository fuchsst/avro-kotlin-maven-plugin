package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

object DoubleFieldBuilder : FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field): String {
        require(field.schema().type == Schema.Type.DOUBLE)
        return "0.0"
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String): String {
        require(schema.type == Schema.Type.DOUBLE)
        return "output.writeDouble($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.DOUBLE)
        return "input.readDouble()"
    }

    override fun toFieldTypeKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.DOUBLE)
        return "Double"
    }
}
