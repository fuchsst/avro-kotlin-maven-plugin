package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.RecordTypeBuilder
import org.apache.avro.Schema

internal class RecordFieldBuilder(override val field: Schema.Field) : FieldBuilder(field) {
    private val recordTypeBuilder = RecordTypeBuilder(field.schema())

    override fun toDefaultValueKotlinCodeString(): String {
        require(field.schema().type == Schema.Type.RECORD)
        return "${field.schema().namespace}.${field.schema().name}()"
    }

    override fun toCustomEncoderPartKotlinCodeString(): String {
        return recordTypeBuilder.toCustomEncoderPartKotlinCodeString(field.name())
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return recordTypeBuilder.toCustomDecoderPartKotlinCodeString()
    }
}
