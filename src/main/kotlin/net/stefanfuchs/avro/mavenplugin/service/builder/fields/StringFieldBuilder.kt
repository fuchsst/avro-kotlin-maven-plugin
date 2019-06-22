package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

object StringFieldBuilder:FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field) :String{
        require(field.schema().type==Schema.Type.STRING)
        return """"""""
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String) :String{
        require(schema.type==Schema.Type.STRING)
        return "output.writeString($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema):String {
        require(schema.type==Schema.Type.STRING)
        return "input.readString()"
    }

    override fun toFieldTypeKotlinCodeString(schema: Schema):String {
        require(schema.type==Schema.Type.STRING)
        return "String"
    }
}
