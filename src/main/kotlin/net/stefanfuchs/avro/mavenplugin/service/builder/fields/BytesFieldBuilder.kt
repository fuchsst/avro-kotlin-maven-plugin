package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

object BytesFieldBuilder:FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field) :String{
        require(field.schema().type==Schema.Type.BYTES)
        return "ByteArray(0)"
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String) :String{
        require(schema.type==Schema.Type.BYTES)
        return "output.writeBytes($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema):String {
        require(schema.type==Schema.Type.BYTES)
        return "input.readBytes(null).array()"
    }

    override fun toFieldTypeKotlinCodeString(schema: Schema):String {
        require(schema.type==Schema.Type.BYTES)
        return "ByteArray"
    }
}
