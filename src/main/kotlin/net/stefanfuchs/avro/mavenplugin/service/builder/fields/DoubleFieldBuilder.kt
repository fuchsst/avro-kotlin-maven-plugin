package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.DoubleTypeBuilder
import org.apache.avro.Schema

internal class DoubleFieldBuilder (override val field: Schema.Field) : FieldBuilder(field) {
    private val doubleTypeBuilder= DoubleTypeBuilder(field.schema())

    override fun toDefaultValueKotlinCodeString(): String {
        return "0.0"
    }

    override fun toCustomEncoderPartKotlinCodeString(): String {
        return doubleTypeBuilder.toCustomEncoderPartKotlinCodeString(field.name())
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return doubleTypeBuilder.toCustomDecoderPartKotlinCodeString()
    }

}
