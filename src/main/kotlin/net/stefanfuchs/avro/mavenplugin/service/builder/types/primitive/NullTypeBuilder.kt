package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class NullTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return "output.writeNull()"
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return "input.readNull()"
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "Any?"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "Any?"
    }
}
