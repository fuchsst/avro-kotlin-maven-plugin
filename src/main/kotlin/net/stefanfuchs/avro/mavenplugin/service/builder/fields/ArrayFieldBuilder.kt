package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.ArrayTypeBuilder
import org.apache.avro.Schema

internal class ArrayFieldBuilder(override val field: Schema.Field) : FieldBuilder(field) {
    private val arrayTypeBuilder = ArrayTypeBuilder(field.schema())

    override fun toDefaultValueKotlinCodeString(): String {
        return "emptyArray()"
    }

    override fun toCustomEncoderPartKotlinCodeString(): String {
        return arrayTypeBuilder.toCustomEncoderPartKotlinCodeString(field.name())
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return arrayTypeBuilder.toCustomDecoderPartKotlinCodeString()
    }

}
