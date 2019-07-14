package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class BooleanTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return "output.writeBoolean($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return "input.readBoolean()"
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "Boolean"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "Boolean"
    }
}
