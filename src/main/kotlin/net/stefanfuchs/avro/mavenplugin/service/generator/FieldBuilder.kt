package net.stefanfuchs.avro.mavenplugin.service.generator

import org.apache.avro.Schema

fun Schema.Field.asConstructorVarString(): String {
    return "${if (this.doc()?.isNotBlank() == true) (" /**\n" + this.doc() + "*/") else ""} val ${this.name()}: ${this.getFieldType()} ${if (this.hasDefaultValue()) " = " + this.defaultVal() else ""}"
}

fun Schema.Field.asAliasGeterSetter(): String? {
    return this
            .aliases()
            .map {
                """var ${it}: ${this.getFieldType()}
                    get() = this.${this.name()}
                    set(value) {
                        this.${this.name()} = value
                    }
                """.trimIndent()
            }
            .joinToString("\n")
}

fun Schema.Field.asGetIndexFieldMapping(): String {
    return "${this.pos()} -> this.${this.name()}"
}

fun Schema.Field.asPutIndexFieldMapping(): String {
    return "${this.pos()} -> this.${this.name()} = `value\$` as ${this.getFieldType()}"
}

fun Schema.Field.asCustomEncoderPart(): String {
    //TODO("write converter for each field type")
    return "TODO"
}

fun Schema.Field.asCustomDecoderPart(): String {
    //TODO("write converter for each field type")
    return "TODO"
}


fun Schema.Field.asCustomDecoderIndexedPart(): String {
    return "${this.pos()} -> { ${this.asCustomDecoderPart()} }"
}


fun Schema.Field.getFieldType(): String {
    return """${this.schema().type}""" // TODO write proper converter for each field type
}
