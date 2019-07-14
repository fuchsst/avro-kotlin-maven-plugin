package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal interface SchemaBuilder {
    fun toFieldTypeKotlinCodeString(): String
    fun toInternalFieldTypeKotlinCodeString(): String
    fun toCustomEncoderPartKotlinCodeString(fieldName: String): String
    fun toCustomDecoderPartKotlinCodeString(): String
}

internal fun getSchemaBuilder(schema: Schema): SchemaBuilder {
    return when (schema.type) {
        Schema.Type.ARRAY -> ArrayTypeBuilder(schema)
        Schema.Type.BOOLEAN -> BooleanTypeBuilder(schema)
        Schema.Type.BYTES -> BytesTypeBuilder(schema)
        Schema.Type.DOUBLE -> DoubleTypeBuilder(schema)
        Schema.Type.ENUM -> EnumTypeBuilder(schema)
        Schema.Type.FIXED -> FixedTypeBuilder(schema)
        Schema.Type.FLOAT -> FloatTypeBuilder(schema)
        Schema.Type.INT -> IntTypeBuilder(schema)
        Schema.Type.LONG -> LongTypeBuilder(schema)
        Schema.Type.MAP -> MapTypeBuilder(schema)
        Schema.Type.NULL -> NullTypeBuilder(schema)
        Schema.Type.RECORD -> RecordTypeBuilder(schema)
        Schema.Type.STRING -> StringTypeBuilder(schema)
        Schema.Type.UNION -> UnionTypeBuilder(schema)
        null -> throw AssertionError("Schema type of ${schema.name} can not be null")
    }
}

internal fun Schema.asCustomEncoderPartKotlinCodeString(fieldName: String): String {
    return getSchemaBuilder(this).toCustomEncoderPartKotlinCodeString(fieldName)
}

internal fun Schema.asCustomDecoderPartKotlinCodeString(): String {
    return getSchemaBuilder(this).toCustomDecoderPartKotlinCodeString()
}


internal fun Schema.asFieldTypeKotlinCodeString(): String {
    return getSchemaBuilder(this).toFieldTypeKotlinCodeString()
}

internal fun Schema.asInternalFieldTypeKotlinCodeString(): String {
    return getSchemaBuilder(this).toInternalFieldTypeKotlinCodeString()
}
