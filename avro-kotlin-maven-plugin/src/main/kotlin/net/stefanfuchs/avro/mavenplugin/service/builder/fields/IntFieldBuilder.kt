package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.IntTypeBuilder
import org.apache.avro.Schema

internal class IntFieldBuilder(override val field: Schema.Field) : FieldBuilder(field, IntTypeBuilder(field.schema())) {

    override fun toDefaultValueKotlinCodeString(): String {
        return "0"
    }

}
