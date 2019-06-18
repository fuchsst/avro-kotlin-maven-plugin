package net.stefanfuchs.avro.mavenplugin.service.generator

import org.apache.avro.JsonProperties
import org.apache.avro.Schema

fun Schema.Field.asConstructorVarKotlinCodeString(): String {
    return "${if (this.doc()?.isNotBlank() == true) (" /**\n" + this.doc() + "*/") else ""} val ${this.name()}: ${this.schema().asFieldTypeKotlinCodeString()} ${if (this.hasDefaultValue()) "= " + (if (this.defaultVal() is JsonProperties.Null) "null" else this.defaultVal()) else ""}".trim()
}

fun Schema.Field.asAliasGetterSetterKotlinCodeString(): String? {
    return this
            .aliases()
            .map {
                """var ${it}: ${this.schema().asFieldTypeKotlinCodeString()}
                    get() = this.${this.name()}
                    set(value) {
                        this.${this.name()} = value
                    }
                """.trimIndent()
            }
            .joinToString("\n")
}

fun Schema.Field.asGetIndexFieldMappingKotlinCodeString(): String {
    return "${this.pos()} -> this.${this.name()}"
}

fun Schema.Field.asPutIndexFieldMappingKotlinCodeString(): String {
    return "${this.pos()} -> this.${this.name()} = `value\$` as ${this.schema().asFieldTypeKotlinCodeString()}"
}

fun Schema.Field.asCustomEncoderPartKotlinCodeString(): String {
    //TODO("write converter for each field type")
    return "TODO"
}

fun Schema.Field.asCustomDecoderPartKotlinCodeString(): String {
    //TODO("write converter for each field type")
    return "TODO"
}


fun Schema.Field.asCustomDecoderIndexedPartKotlinCodeString(): String {
    return "${this.pos()} -> ( ${this.asCustomDecoderPartKotlinCodeString()} )"
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
        Schema.Type.BYTES -> "ByteBuffer"
        Schema.Type.UNION -> if (this.types.size == 2 && this.types[0].type == Schema.Type.NULL) "${this.types[1].asFieldTypeKotlinCodeString()}?" else "Any?"
        Schema.Type.NULL -> "Any?"
        else -> throw IllegalArgumentException()
    }
}
