package net.stefanfuchs.avro.mavenplugin.model.input

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
    CLASSNAME(true);

    val code = this.name.toLowerCase()
    val isComplex: Boolean by lazy { this in listOf(MAP, RECORD, ARRAY, ENUM, UNION, FIXED) }
    val isKnown: Boolean by lazy { this !in listOf(CLASSNAME) }
}
