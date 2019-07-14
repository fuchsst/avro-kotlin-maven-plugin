package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class RecordTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return "$fieldName.customEncode(output)"
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return "${schema.namespace}.${schema.name}().apply { customDecode(input) }"
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "${schema.namespace}.${schema.name}"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "${schema.namespace}.${schema.name}"
    }
}
