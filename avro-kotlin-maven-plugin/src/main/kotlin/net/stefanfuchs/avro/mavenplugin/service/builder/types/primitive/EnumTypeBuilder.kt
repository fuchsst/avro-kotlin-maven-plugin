package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class EnumTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return "output.writeEnum($fieldName!!.ordinal)"
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return "${schema.namespace}.${schema.name}.values()[input.readEnum()]"
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "${schema.namespace}.${schema.name}"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "${schema.namespace}.${schema.name}"
    }
}
