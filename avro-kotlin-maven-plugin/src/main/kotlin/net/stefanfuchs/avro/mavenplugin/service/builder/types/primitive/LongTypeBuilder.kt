package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class LongTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return "output.writeLong($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return "input.readLong()"
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "Long"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "Long"
    }
}
