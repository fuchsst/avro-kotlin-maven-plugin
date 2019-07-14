package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.DoubleTypeBuilder
import org.apache.avro.Schema

internal class DoubleFieldBuilder(override val field: Schema.Field) : FieldBuilder(field, DoubleTypeBuilder(field.schema())) {

    override fun toDefaultValueKotlinCodeString(): String {
        return "0.0"
    }


}
