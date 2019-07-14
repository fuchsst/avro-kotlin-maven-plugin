package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class FloatTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return "output.writeFloat($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return "input.readFloat()"
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "Float"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "Float"
    }
}
