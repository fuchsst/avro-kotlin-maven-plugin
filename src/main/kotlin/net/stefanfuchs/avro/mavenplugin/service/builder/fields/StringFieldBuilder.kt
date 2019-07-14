package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.StringTypeBuilder
import org.apache.avro.Schema

internal class StringFieldBuilder(override val field: Schema.Field) : FieldBuilder(field, StringTypeBuilder(field.schema())) {

    override fun toDefaultValueKotlinCodeString(): String {
        return """"""""
    }

}
