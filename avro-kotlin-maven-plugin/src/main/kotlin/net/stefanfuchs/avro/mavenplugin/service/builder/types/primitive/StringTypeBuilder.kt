package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

//TODO handle Strings internally as org.apache.avro.util.Utf8
internal class StringTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return "output.writeString($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return "input.readString()"
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "String"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "org.apache.avro.util.Utf8"
    }
}
