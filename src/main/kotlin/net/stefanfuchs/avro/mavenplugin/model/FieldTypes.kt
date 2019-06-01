package net.stefanfuchs.avro.mavenplugin.model

enum class FieldTypes {
    /** a map String->Any **/
    MAP,
    /** class **/
    RECORD,
    /** list **/
    ARRAY,
    /** enumaration class **/
    ENUM,
    /** list of types **/
    UNION,
    /** bytearray **/
    FIXED,
    /** one of the types listed in [PrimitiveFieldType] **/
    PRIMITIVE;

    val code = this.name.toLowerCase()
}
