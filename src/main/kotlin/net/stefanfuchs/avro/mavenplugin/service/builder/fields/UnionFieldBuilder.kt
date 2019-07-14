package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.UnionTypeBuilder
import org.apache.avro.Schema

internal class UnionFieldBuilder(override val field: Schema.Field) : FieldBuilder(field) {
    private val unionTypeBuilder = UnionTypeBuilder(field.schema())

    override fun toDefaultValueKotlinCodeString(): String {
        return "null"
    }

    override fun toCustomEncoderPartKotlinCodeString(): String {
        return unionTypeBuilder.toCustomEncoderPartKotlinCodeString(field.name())
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return unionTypeBuilder.toCustomDecoderPartKotlinCodeString()
    }

}
