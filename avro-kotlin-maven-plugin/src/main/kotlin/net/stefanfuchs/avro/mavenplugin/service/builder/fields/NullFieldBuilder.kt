package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.NullTypeBuilder
import org.apache.avro.Schema

internal class NullFieldBuilder(override val field: Schema.Field) : FieldBuilder(field, NullTypeBuilder(field.schema())) {

    override fun toDefaultValueKotlinCodeString(): String {
        require(field.schema().type == Schema.Type.NULL)
        return "Any?"
    }

}
