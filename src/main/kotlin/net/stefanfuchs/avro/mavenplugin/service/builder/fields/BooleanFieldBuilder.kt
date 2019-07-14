package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.BooleanTypeBuilder
import org.apache.avro.Schema

internal class BooleanFieldBuilder(override val field: Schema.Field) : FieldBuilder(field) {
    private val booleanTypeBuilder = BooleanTypeBuilder(field.schema())

    override fun toDefaultValueKotlinCodeString(): String {
        return "false"
    }

    override fun toCustomEncoderPartKotlinCodeString(): String {
        return booleanTypeBuilder.toCustomEncoderPartKotlinCodeString(field.name())
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return booleanTypeBuilder.toCustomDecoderPartKotlinCodeString()
    }
}
