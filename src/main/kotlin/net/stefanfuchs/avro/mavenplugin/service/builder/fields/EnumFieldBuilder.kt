package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.EnumTypeBuilder
import org.apache.avro.Schema

internal class EnumFieldBuilder(override val field: Schema.Field) : FieldBuilder(field, EnumTypeBuilder(field.schema())) {


    override fun toDefaultValueKotlinCodeString(): String {
        return "${field.schema().name}.${field.schema().enumSymbols[0]}"
    }

}
