package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.StringTypeBuilder
import org.apache.avro.Schema

internal class StringFieldBuilder(override val field: Schema.Field) : FieldBuilder(field, StringTypeBuilder(field.schema())) {

    override fun toDefaultValueKotlinCodeString(): String {
        return """"""""
    }


    override fun toPutIndexFieldMappingKotlinCodeString(): String {
        return "${field.pos()} -> this.${field.name()} = if (`value\$` is org.apache.avro.util.Utf8) `value\$`.toString() else (`value\$` as String)"
    }

}
