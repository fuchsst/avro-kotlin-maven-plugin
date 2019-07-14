package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class FixedTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return "output.writeFixed($fieldName.bytes(), 0, ${schema.fixedSize})"
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return "{ val bytes = ByteArray(${schema.fixedSize}); input.readFixed(bytes, 0, ${schema.fixedSize}); ${schema.namespace}.${schema.name}(bytes) }.invoke()"
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "${schema.namespace}.${schema.name}"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "${schema.namespace}.${schema.name}"
    }
}
