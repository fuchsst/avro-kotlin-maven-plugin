package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class BytesTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return "output.writeBytes($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return "input.readBytes(null).array()"
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "ByteArray"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "ByteArray"
    }
}
