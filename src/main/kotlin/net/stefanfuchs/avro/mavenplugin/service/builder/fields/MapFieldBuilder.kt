package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

object MapFieldBuilder : FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field): String {
        require(field.schema().type == Schema.Type.MAP)
        return "mutableMapOf()"
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String): String {
        require(schema.type == Schema.Type.MAP)
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

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.MAP)
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

    override fun toFieldTypeKotlinCodeString(schema: Schema): String {
        require(schema.type == Schema.Type.MAP)
        return "Map<String, ${schema.valueType.asFieldTypeKotlinCodeString()}>"
    }
}
