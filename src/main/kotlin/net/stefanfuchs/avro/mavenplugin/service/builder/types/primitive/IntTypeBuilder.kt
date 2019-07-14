package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class IntTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return "output.writeInt($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return "input.readInt()"
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "Int"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "Int"
    }
}
