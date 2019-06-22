package net.stefanfuchs.avro.mavenplugin.service.generator

import org.apache.avro.JsonProperties
import org.apache.avro.Schema

fun Schema.Field.asConstructorVarKotlinCodeString(): String {
    return "${if (this.doc()?.isNotBlank() == true) (" /**\n" + this.doc() + "*/") else ""} var ${this.name()}: ${this.schema().asFieldTypeKotlinCodeString()} = ${this.asDefaultValueKotlinCodeString()}".trim()
}

private fun Schema.Field.asDefaultValueKotlinCodeString(): String {
    return if (this.hasDefaultValue()) {
        if (this.defaultVal() is JsonProperties.Null) "null" else this.defaultVal().toString()
    } else if (this.schema().isNullable) {
        "null"
    } else {
        when (this.schema().type) {
            Schema.Type.ENUM -> throw IllegalArgumentException("Enum ${this.name()} must be nullable or have default value")
            Schema.Type.RECORD -> "${this.schema().namespace}.${this.schema().name}()"
            Schema.Type.FIXED -> "${this.schema().namespace}.${this.schema().name}()"
            Schema.Type.BOOLEAN -> "false"
            Schema.Type.DOUBLE -> "0.0"
            Schema.Type.FLOAT -> "0F"
            Schema.Type.INT -> "0"
            Schema.Type.LONG -> "0L"
            Schema.Type.STRING -> """"""""
            Schema.Type.MAP -> "mutableMapOf()"
            Schema.Type.ARRAY -> "emptyArray()"
            Schema.Type.BYTES -> "ByteArray(0)"
            Schema.Type.UNION -> "null"
            Schema.Type.NULL -> "Any?"
            else -> throw IllegalArgumentException()
        }
    }
}

fun Schema.Field.asAliasGetterSetterKotlinCodeString(): String? {
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

fun Schema.Field.asGetIndexFieldMappingKotlinCodeString(): String {
    return "${this.pos()} -> this.${this.name()}"
}

fun Schema.Field.asPutIndexFieldMappingKotlinCodeString(): String {
    return "${this.pos()} -> this.${this.name()} = `value\$` as ${this.schema().asFieldTypeKotlinCodeString()}"
}

fun Schema.Field.asCustomEncoderPartKotlinCodeString(): String {
    //TODO("write converter for each field type")
    return "TODO()"
}

fun Schema.asCustomDecoderPartKotlinCodeString(): String {
    return when (this.type) {
        Schema.Type.ENUM -> "net.stefanfuchs.avro.mavenplugin.test.pkg.Suit.values()[input.readEnum()]"
        Schema.Type.RECORD -> "${this.namespace}.${this.name}().apply { customDecode(input) }"
        Schema.Type.FIXED -> "{ val bytes = ByteArray(${this.fixedSize}); input.readFixed(bytes, 0, ${this.fixedSize}); ${this.namespace}.${this.name}(bytes) }.invoke()"
        Schema.Type.BOOLEAN -> "input.readBoolean()"
        Schema.Type.DOUBLE -> "input.readDouble()"
        Schema.Type.FLOAT -> "input.readFloat()"
        Schema.Type.INT -> "input.readInt()"
        Schema.Type.LONG -> "input.readLong()"
        Schema.Type.STRING -> "input.readString()"
        Schema.Type.MAP -> """mutableMapOf<String, ${this.valueType.asFieldTypeKotlinCodeString()}>().apply {
                                                                     var size = input.readMapStart()
                                                                     while (0 < size) {
                                                                         while (size != 0L) {
                                                                             val key = input.readString()
                                                                             val value = ${this.valueType.asCustomDecoderPartKotlinCodeString()}
                                                                             this[key] = value
                                                                             size--
                                                                         }
                                                                         size = input.mapNext()
                                                                     }         
                                                                 }            
                           """.trimIndent()
        Schema.Type.ARRAY -> """mutableListOf<${this.elementType.asFieldTypeKotlinCodeString()}>().apply { var size = input.readArrayStart()
                                                                     while ( size>0) {
                                                                         while (size != 0L) {
                                                                             this.add(${this.elementType.asCustomDecoderPartKotlinCodeString()})
                                                                             size--
                                                                         }
                                                                         size = input.arrayNext()
                                                                     }
                                                                 }.toTypedArray()
                             """
        Schema.Type.BYTES -> "input.readBytes(null).array()"
        Schema.Type.UNION -> """if (input.readIndex() != 1) {
                                    input.readNull()
                                    null
                                } else {
                                    ${this.types[1].asCustomDecoderPartKotlinCodeString()}
                                }
                             """
        Schema.Type.NULL -> "Any?"
        else -> throw IllegalArgumentException()
    }

}


fun Schema.asFieldTypeKotlinCodeString(): String {
    return when (this.type) {
        Schema.Type.ENUM -> "${this.namespace}.${this.name}"
        Schema.Type.RECORD -> "${this.namespace}.${this.name}"
        Schema.Type.FIXED -> "${this.namespace}.${this.name}"
        Schema.Type.BOOLEAN -> "Boolean"
        Schema.Type.DOUBLE -> "Double"
        Schema.Type.FLOAT -> "Float"
        Schema.Type.INT -> "Int"
        Schema.Type.LONG -> "Long"
        Schema.Type.STRING -> "String"
        Schema.Type.MAP -> "Map<String, ${this.valueType.asFieldTypeKotlinCodeString()}>"
        Schema.Type.ARRAY -> "Array<${this.elementType.asFieldTypeKotlinCodeString()}>"
        Schema.Type.BYTES -> "ByteArray"
        Schema.Type.UNION -> if (this.types.size == 2 && this.types[0].type == Schema.Type.NULL) "${this.types[1].asFieldTypeKotlinCodeString()}?" else "Any?"
        Schema.Type.NULL -> "Any?"
        else -> throw IllegalArgumentException()
    }
}
