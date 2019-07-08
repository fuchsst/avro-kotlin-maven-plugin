package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

internal object ArrayFieldBuilder : FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field): String {
        require(field.schema().type == Schema.Type.ARRAY)
        return "emptyArray()"
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String): String {
        require(schema.type == Schema.Type.ARRAY)
        return """
                                run {
                                    val arraySize = $fieldName.size.toLong()
                                    output.writeArrayStart()
                                    output.setItemCount(arraySize)
                                    var actualArraySize: Long = 0
                                    $fieldName.forEach {
                                        actualArraySize++
                                        output.startItem()
                                        ${schema.elementType.asCustomEncoderPartKotlinCodeString("it")}
                                    }
                                    output.writeArrayEnd()
                                    if (actualArraySize != arraySize)
                                        throw java.util.ConcurrentModificationException("Array-size written was ${'$'}arraySize, but element count was ${'$'}actualArraySize.")
                                }
        """.trimIndent()
    }

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.ARRAY)
        return """mutableListOf<${schema.elementType.asFieldTypeKotlinCodeString()}>().apply { var size = input.readArrayStart()
                                                                     while ( size>0) {
                                                                         while (size != 0L) {
                                                                             this.add(${schema.elementType.asCustomDecoderPartKotlinCodeString()})
                                                                             size--
                                                                         }
                                                                         size = input.arrayNext()
                                                                     }
                                                                 }.toTypedArray()
                             """.trimIndent()
    }

    override fun toFieldTypeKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.ARRAY)
        return "Array<${schema.elementType.asFieldTypeKotlinCodeString()}>"
    }
}
