package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class ArrayTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
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

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return """mutableListOf<${schema.elementType.asFieldTypeKotlinCodeString()}>().apply { var size = input.readArrayStart()
                                                                     while (size > 0) {
                                                                         while (size != 0L) {
                                                                             this.add(${schema.elementType.asCustomDecoderPartKotlinCodeString()})
                                                                             size--
                                                                         }
                                                                         size = input.arrayNext()
                                                                     }
                                                                 }.toTypedArray()
               """.trimIndent()
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "Array<${schema.elementType.asFieldTypeKotlinCodeString()}>"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "Array<${schema.elementType.asFieldTypeKotlinCodeString()}>"
    }
}
