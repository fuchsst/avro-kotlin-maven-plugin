package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.UnionTypeBuilder
import org.apache.avro.Schema

internal class UnionFieldBuilder(override val field: Schema.Field) : FieldBuilder(field, UnionTypeBuilder(field.schema())) {

    override fun toDefaultValueKotlinCodeString(): String {
        return "null"
    }

}
