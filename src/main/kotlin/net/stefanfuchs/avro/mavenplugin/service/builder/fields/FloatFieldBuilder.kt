package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.FloatTypeBuilder
import org.apache.avro.Schema

internal class FloatFieldBuilder(override val field: Schema.Field) : FieldBuilder(field) {
    private val floatTypeBuilder = FloatTypeBuilder(field.schema())

    override fun toDefaultValueKotlinCodeString(): String {
        return "0F"
    }

    override fun toCustomEncoderPartKotlinCodeString(): String {
        return floatTypeBuilder.toCustomEncoderPartKotlinCodeString(field.name())
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return floatTypeBuilder.toCustomDecoderPartKotlinCodeString()
    }
}
