package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.BooleanTypeBuilder
import org.apache.avro.Schema

internal class BooleanFieldBuilder(override val field: Schema.Field) : FieldBuilder(field, BooleanTypeBuilder(field.schema())) {

    override fun toDefaultValueKotlinCodeString(): String {
        return "false"
    }

}
