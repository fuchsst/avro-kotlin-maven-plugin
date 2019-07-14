package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.FloatTypeBuilder
import org.apache.avro.Schema

internal class FloatFieldBuilder(override val field: Schema.Field) : FieldBuilder(field, FloatTypeBuilder(field.schema())) {

    override fun toDefaultValueKotlinCodeString(): String {
        return "0F"
    }

}
