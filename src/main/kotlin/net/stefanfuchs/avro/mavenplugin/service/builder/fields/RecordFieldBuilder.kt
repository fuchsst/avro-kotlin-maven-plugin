package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

internal object RecordFieldBuilder : FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field): String {
        require(field.schema().type == Schema.Type.RECORD)
        return "${field.schema().namespace}.${field.schema().name}()"
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String): String {
        require(schema.type == Schema.Type.RECORD)
        return "$fieldName.customEncode(output)"
    }

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.RECORD)
        return "${schema.namespace}.${schema.name}().apply { customDecode(input) }"
    }

    override fun toFieldTypeKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.RECORD)
        return "${schema.namespace}.${schema.name}"
    }
}
