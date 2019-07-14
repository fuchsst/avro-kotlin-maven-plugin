package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.NullTypeBuilder
import org.apache.avro.Schema

internal class NullFieldBuilder(override val field: Schema.Field) : FieldBuilder(field) {
    private val nullTypeBuilder = NullTypeBuilder(field.schema())

    override fun toDefaultValueKotlinCodeString(): String {
        require(field.schema().type == Schema.Type.NULL)
        return "Any?"
    }

    override fun toCustomEncoderPartKotlinCodeString(): String {
        return nullTypeBuilder.toCustomEncoderPartKotlinCodeString(field.name())
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return nullTypeBuilder.toCustomDecoderPartKotlinCodeString()
    }
}
