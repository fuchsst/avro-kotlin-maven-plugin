package net.stefanfuchs.avro.mavenplugin.model

enum class FieldType(val isPrimitive: Boolean = false) {
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
    /** primitive types **/
    NULL(true),
    BOOLEAN(true),
    INT(true),
    LONG(true),
    FLOAT(true),
    DOUBLE(true),
    BYTES(true),
    STRING(true),
    OTHER_PRIMITIVE(true);

    val code = this.name.toLowerCase()
}
