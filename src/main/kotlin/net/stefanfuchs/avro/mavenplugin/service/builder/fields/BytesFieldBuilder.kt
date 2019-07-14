package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.BytesTypeBuilder
import org.apache.avro.Schema

internal class BytesFieldBuilder (override val field: Schema.Field) : FieldBuilder(field) {
    private val bytesTypeBuilder=BytesTypeBuilder(field.schema())

    override fun toDefaultValueKotlinCodeString(): String {
        return "ByteArray(0)"
    }

    override fun toCustomEncoderPartKotlinCodeString(): String {
        return bytesTypeBuilder.toCustomEncoderPartKotlinCodeString(field.name())
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return bytesTypeBuilder.toCustomDecoderPartKotlinCodeString()
    }
}
