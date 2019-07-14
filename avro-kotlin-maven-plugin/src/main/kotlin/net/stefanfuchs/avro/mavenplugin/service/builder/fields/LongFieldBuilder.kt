package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.LongTypeBuilder
import org.apache.avro.Schema

internal class LongFieldBuilder(override val field: Schema.Field) : FieldBuilder(field, LongTypeBuilder(field.schema())) {

    override fun toDefaultValueKotlinCodeString(): String {
        return "0L"
    }

}
