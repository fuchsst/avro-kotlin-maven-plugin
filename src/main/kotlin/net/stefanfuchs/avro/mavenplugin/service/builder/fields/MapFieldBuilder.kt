package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.MapTypeBuilder
import org.apache.avro.Schema

internal class MapFieldBuilder(override val field: Schema.Field) : FieldBuilder(field, MapTypeBuilder(field.schema())) {

    override fun toDefaultValueKotlinCodeString(): String {
        return "mutableMapOf()"
    }

}
