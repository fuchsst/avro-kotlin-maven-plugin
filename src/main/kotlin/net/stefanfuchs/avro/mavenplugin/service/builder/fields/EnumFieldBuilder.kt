package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

object EnumFieldBuilder : FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field): String {
        require(field.schema().type == Schema.Type.ENUM)
        return "${field.schema().name}.${field.schema().enumSymbols[0]}"
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String): String {
        require(schema.type == Schema.Type.ENUM)
        return "output.writeEnum($fieldName!!.ordinal)"
    }

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.ENUM)
        return "${schema.namespace}.${schema.name}.values()[input.readEnum()]"
    }

    override fun toFieldTypeKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.ENUM)
        return "${schema.namespace}.${schema.name}"
    }
}
