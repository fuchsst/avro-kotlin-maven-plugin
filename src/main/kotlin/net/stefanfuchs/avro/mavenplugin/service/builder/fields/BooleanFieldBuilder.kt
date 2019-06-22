package net.stefanfuchs.avro.mavenplugin.service.builder.fields

import org.apache.avro.Schema

object BooleanFieldBuilder:FieldBuilder {
    override fun toDefaultValueKotlinCodeString(field: Schema.Field) :String{
        require(field.schema().type==Schema.Type.BOOLEAN)
        return "false"
    }

    override fun toCustomEncoderPartKotlinCodeString(schema: Schema, fieldName: String) :String{
        require(schema.type==Schema.Type.BOOLEAN)
        return "output.writeBoolean($fieldName)"
    }

    override fun toCustomDecoderPartKotlinCodeString(schema: Schema):String {
        require(schema.type==Schema.Type.BOOLEAN)
        return "input.readBoolean()"
    }

    override fun toFieldTypeKotlinCodeString(schema: Schema):String {
        require(schema.type==Schema.Type.BOOLEAN)
        return "Boolean"
    }
}
