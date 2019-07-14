package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.FixedTypeBuilder
import org.apache.avro.Schema

internal class FixedFieldBuilder(override val field: Schema.Field) : FieldBuilder(field, FixedTypeBuilder(field.schema())) {

    override fun toDefaultValueKotlinCodeString(): String {
        return "${field.schema().namespace}.${field.schema().name}()"
    }

}
