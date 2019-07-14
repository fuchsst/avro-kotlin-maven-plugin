package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.MapTypeBuilder
import org.apache.avro.Schema

internal class MapFieldBuilder(override val field: Schema.Field) : FieldBuilder(field) {
    private val mapTypeBuilder = MapTypeBuilder(field.schema())

    override fun toDefaultValueKotlinCodeString(): String {
        return "mutableMapOf()"
    }

    override fun toCustomEncoderPartKotlinCodeString(): String {
        return mapTypeBuilder.toCustomEncoderPartKotlinCodeString(field.name())
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return mapTypeBuilder.toCustomDecoderPartKotlinCodeString()
    }
}
