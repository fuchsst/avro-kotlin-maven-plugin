package net.stefanfuchs.avro.mavenplugin.service.builder.types.primitive

import org.apache.avro.Schema

internal class MapTypeBuilder(val schema: Schema) : SchemaBuilder {
    override fun toCustomEncoderPartKotlinCodeString(fieldName: String): String {
        return """
                  run {
                                val mapSize = $fieldName.size.toLong()
                                output.writeMapStart()
                                output.setItemCount(mapSize)
                                var actualMapSize: Long = 0
                                for ((key, value) in $fieldName) {
                                    actualMapSize++
                                    output.startItem()
                                    output.writeString(key)
                                    ${schema.valueType.asCustomEncoderPartKotlinCodeString("value")}
                                }
                                output.writeMapEnd()
                                if (actualMapSize != mapSize)
                                    throw java.util.ConcurrentModificationException("Map-size written was ${'$'}mapSize, but element count was ${'$'}actualMapSize.")
                            }
        """.trimIndent()
    }

    override fun toCustomDecoderPartKotlinCodeString(): String {
        return """mutableMapOf<String, ${schema.valueType.asFieldTypeKotlinCodeString()}>().apply {
                                                                     var size = input.readMapStart()
                                                                     while (0 < size) {
                                                                         while (size != 0L) {
                                                                             val key = input.readString()
                                                                             val value = ${schema.valueType.asCustomDecoderPartKotlinCodeString()}
                                                                             this[key] = value
                                                                             size--
                                                                         }
                                                                         size = input.mapNext()
                                                                     }         
                                                                 }            
                           """.trimIndent()
    }

    override fun toFieldTypeKotlinCodeString(): String {
        return "Map<String, ${schema.valueType.asFieldTypeKotlinCodeString()}>"
    }

    override fun toInternalFieldTypeKotlinCodeString(): String {
        return "Map<String, ${schema.valueType.asFieldTypeKotlinCodeString()}>"
    }
}
