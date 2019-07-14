package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.StringTypeBuilder
import org.apache.avro.Schema

internal class StringFieldBuilder(override val field: Schema.Field) : FieldBuilder(field) {
    private val stringTypeBuilder = StringTypeBuilder(field.schema())

    override fun toDefaultValueKotlinCodeString(): String {
        return """"""""
    }

    override fun toCustomEncoderPartKotlinCodeString(): String {
        return stringTypeBuilder.toCustomEncoderPartKotlinCodeString(field.name())
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return stringTypeBuilder.toCustomDecoderPartKotlinCodeString()
    }
}
