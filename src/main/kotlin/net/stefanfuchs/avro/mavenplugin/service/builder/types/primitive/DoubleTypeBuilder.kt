package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class DoubleTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return "output.writeDouble($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return "input.readDouble()"
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "Double"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "Double"
    }
}
