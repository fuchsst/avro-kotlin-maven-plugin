package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.JsonProperties
import org.apache.avro.Schema

internal object FieldBuilderFactory {
    private val SchemaTypeToFieldBuilderMap: Map<Schema.Type, FieldBuilder> = mapOf(
            Schema.Type.ENUM to EnumFieldBuilder,
            Schema.Type.RECORD to RecordFieldBuilder,
            Schema.Type.FIXED to FixedFieldBuilder,
            Schema.Type.BOOLEAN to BooleanFieldBuilder,
            Schema.Type.DOUBLE to DoubleFieldBuilder,
            Schema.Type.FLOAT to FloatFieldBuilder,
            Schema.Type.INT to IntFieldBuilder,
            Schema.Type.LONG to LongFieldBuilder,
            Schema.Type.STRING to StringFieldBuilder,
            Schema.Type.MAP to MapFieldBuilder,
            Schema.Type.ARRAY to ArrayFieldBuilder,
            Schema.Type.BYTES to BytesFieldBuilder,
            Schema.Type.UNION to UnionFieldBuilder,
            Schema.Type.NULL to NullFieldBuilder
    )

    fun fromSchemaType(type: Schema.Type): FieldBuilder {
        requireNotNull(SchemaTypeToFieldBuilderMap[type]) { "No FieldBuilder implemented for schema type '$type'!" }
        return SchemaTypeToFieldBuilderMap[type]!!
    }
}

internal interface FieldBuilder {
    fun toDefaultValueKotlinCodeString(field: Schema.Field): String
    fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String): String
    fun toCustomDecoderPartKotlinCodeString(schema: Schema): String
    fun toFieldTypeKotlinCodeString(schema: Schema): String
}

internal fun Schema.Field.asConstructorVarKotlinCodeString(): String {
    return "${if (this.doc()?.isNotBlank() == true) (" /**\n" + this.doc() + "*/") else ""} var ${this.name()}: ${this.schema().asFieldTypeKotlinCodeString()} = ${this.asDefaultValueKotlinCodeString()}".trim()
}

private fun Schema.Field.asDefaultValueKotlinCodeString(): String {
    return if (this.hasDefaultValue()) {
        if (this.defaultVal() is JsonProperties.Null) "null" else this.defaultVal().toString()
    } else if (this.schema().isNullable) {
        "null"
    } else {
        FieldBuilderFactory.fromSchemaType(this.schema().type).toDefaultValueKotlinCodeString(this)
    }
}

internal fun Schema.Field.asAliasGetterSetterKotlinCodeString(): String? {
    return this
            .aliases().joinToString("\n") {
                """var $it: ${this.schema().asFieldTypeKotlinCodeString()}
                        get() = this.${this.name()}
                        set(value) {
                            this.${this.name()} = value
                        }
                    """.trimIndent()
            }
}

internal fun Schema.Field.asGetIndexFieldMappingKotlinCodeString(): String {
    return "${this.pos()} -> this.${this.name()}"
}

internal fun Schema.Field.asPutIndexFieldMappingKotlinCodeString(): String {
    return "${this.pos()} -> this.${this.name()} = `value\$` as ${this.schema().asFieldTypeKotlinCodeString()}"
}

internal fun Schema.asCustomEncoderPartKotlinCodeString(fieldName: String): String {
    return FieldBuilderFactory.fromSchemaType(this.type).toCustomEncoderPartKotlinCodeString(this, fieldName)
}

internal fun Schema.asCustomDecoderPartKotlinCodeString(): String {
    return FieldBuilderFactory.fromSchemaType(this.type).toCustomDecoderPartKotlinCodeString(this)
}

internal fun Schema.asFieldTypeKotlinCodeString(): String {
    return FieldBuilderFactory.fromSchemaType(this.type).toFieldTypeKotlinCodeString(this)
}
