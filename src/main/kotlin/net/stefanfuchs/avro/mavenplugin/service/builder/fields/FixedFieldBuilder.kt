package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

internal object FixedFieldBuilder : FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field): String {
        require(field.schema().type == Schema.Type.FIXED)
        return "${field.schema().namespace}.${field.schema().name}()"
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String): String {
        require(schema.type == Schema.Type.FIXED)
        return "output.writeFixed($fieldName.bytes(), 0, ${schema.fixedSize})"
    }

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.FIXED)
        return "{ val bytes = ByteArray(${schema.fixedSize}); input.readFixed(bytes, 0, ${schema.fixedSize}); ${schema.namespace}.${schema.name}(bytes) }.invoke()"
    }

    override fun toFieldTypeKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.FIXED)
        return "${schema.namespace}.${schema.name}"
    }
}
