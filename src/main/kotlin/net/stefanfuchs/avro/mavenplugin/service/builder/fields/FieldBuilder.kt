package net.stefanfuchs.avro.mavenplugin.service.builder.fields


import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.SchemaBuilder
import net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive.getSchemaBuilder
import org.apache.avro.JsonProperties
import org.apache.avro.Schema


internal abstract class FieldBuilder(open val field: Schema.Field, open val fieldTypeBuilder: SchemaBuilder) {
    companion object {
        fun getFieldBuilder(field: Schema.Field): FieldBuilder {
            return when (field.schema().type) {
                Schema.Type.ARRAY -> ArrayFieldBuilder(field)
                Schema.Type.BOOLEAN -> BooleanFieldBuilder(field)
                Schema.Type.BYTES -> BytesFieldBuilder(field)
                Schema.Type.DOUBLE -> DoubleFieldBuilder(field)
                Schema.Type.ENUM -> EnumFieldBuilder(field)
                Schema.Type.FIXED -> FixedFieldBuilder(field)
                Schema.Type.FLOAT -> FloatFieldBuilder(field)
                Schema.Type.INT -> IntFieldBuilder(field)
                Schema.Type.LONG -> LongFieldBuilder(field)
                Schema.Type.MAP -> MapFieldBuilder(field)
                Schema.Type.NULL -> NullFieldBuilder(field)
                Schema.Type.RECORD -> RecordFieldBuilder(field)
                Schema.Type.STRING -> StringFieldBuilder(field)
                Schema.Type.UNION -> UnionFieldBuilder(field)
                null -> throw AssertionError("Schema type of field ${field.name()} can not be null")
            }
        }
    }


    abstract fun toDefaultValueKotlinCodeString(): String

    fun toCustomEncoderPartKotlinCodeString(): String {
        return fieldTypeBuilder.toCustomEncoderPartKotlinCodeString(field.name())
    }

    fun toCustomDecoderPartKotlinCodeString(): String {
        return fieldTypeBuilder.toCustomDecoderPartKotlinCodeString()
    }


    fun asConstructorVarKotlinCodeString(): String {
        return "${if (field.doc()?.isNotBlank() == true) (" /**\n" + field.doc() + "*/") else ""} var ${field.name()}: ${field.asFieldTypeKotlinCodeString()} = ${field.asDefaultValueKotlinCodeString()}".trim()
    }

    fun asDefaultValueKotlinCodeString(): String {
        return if (field.hasDefaultValue()) {
            if (field.defaultVal() is JsonProperties.Null) "null" else field.defaultVal().toString()
        } else if (field.schema().isNullable) {
            "null"
        } else {
            getFieldBuilder(field).toDefaultValueKotlinCodeString()
        }
    }

    fun asAliasGetterSetterKotlinCodeString(): String? {
        return field
                .aliases().joinToString("\n") {
                    """var $it: ${field.asFieldTypeKotlinCodeString()}
                        get() = this.${field.name()}
                        set(value) {
                            this.${field.name()} = value
                        }
                    """.trimIndent()
                }
    }
}


internal fun Schema.Field.asConstructorVarKotlinCodeString(): String {
    return FieldBuilder.getFieldBuilder(this).asConstructorVarKotlinCodeString()
}

internal fun Schema.Field.asDefaultValueKotlinCodeString(): String {
    return FieldBuilder.getFieldBuilder(this).asDefaultValueKotlinCodeString()
}

internal fun Schema.Field.asAliasGetterSetterKotlinCodeString(): String? {
    return FieldBuilder.getFieldBuilder(this).asAliasGetterSetterKotlinCodeString()
}

internal fun Schema.Field.asGetIndexFieldMappingKotlinCodeString(): String {
    return "${this.pos()} -> this.${this.name()}"
}

internal fun Schema.Field.asPutIndexFieldMappingKotlinCodeString(): String {
    return "${this.pos()} -> this.${this.name()} = `value\$` as ${this.asInternalFieldTypeKotlinCodeString()}"
}

internal fun Schema.Field.asCustomEncoderPartKotlinCodeString(): String {
    return FieldBuilder.getFieldBuilder(this).toCustomEncoderPartKotlinCodeString()
}

internal fun Schema.Field.asCustomDecoderPartKotlinCodeString(): String {
    return FieldBuilder.getFieldBuilder(this).toCustomDecoderPartKotlinCodeString()
}

internal fun Schema.Field.asFieldTypeKotlinCodeString(): String {
    return getSchemaBuilder(this.schema()).toFieldTypeKotlinCodeString()
}

internal fun Schema.Field.asInternalFieldTypeKotlinCodeString(): String {
    return getSchemaBuilder(this.schema()).toInternalFieldTypeKotlinCodeString()
}
