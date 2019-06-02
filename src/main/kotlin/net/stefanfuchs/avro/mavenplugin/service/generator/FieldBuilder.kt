package net.stefanfuchs.avro.mavenplugin.service.generator

import org.apache.avro.Schema

fun Schema.Field.asConstructorVarString(): String {
    return "${if (this.doc()?.isNotBlank() == true) (" /**\n" + this.doc() + "*/") else ""} val ${this.name()}: ${this.schema().type} ${if (this.hasDefaultValue()) "= " + this.defaultVal() else ""}"
}

fun Schema.Field.asAliasGeterSetter(): String {
    return this
            .aliases()
            .map {
                """
                var ${it}: ${this.schema().type}
                    get() = this.${this.name()}
                    set(value) {
                        this.${this.name()} = value
                    }
                """.trimIndent()
            }
            .joinToString { "\n\n" }
}

fun Schema.Field.asGetIndexFieldMapping(): String {
    return "${this.pos()} -> this.${this.name()}"
}

fun Schema.Field.asPutIndexFieldMapping(): String {
    return "${this.pos()} -> this.${this.name()} = `value\$` as ${this.schema().type}"
}

fun Schema.Field.asCustomEncoderPart(): String {
    //TODO("write converter for each field type")
    return "${this.pos()} -> this.${this.name()} = `value\$` as ${this.schema().type}"
}

fun Schema.Field.asCustomDecoderPart(): String {
    //TODO("write converter for each field type")
    return "${this.pos()} -> this.${this.name()} = `value\$` as ${this.schema().type}"
}


fun Schema.Field.asCustomDecoderPartIndexedPart(): String {
    return "${this.pos()} -> { ${this.asCustomDecoderPart()} }"
}
