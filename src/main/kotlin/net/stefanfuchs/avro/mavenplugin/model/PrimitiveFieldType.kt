package net.stefanfuchs.avro.mavenplugin.model

enum class PrimitiveFieldType {
    /** nullable field **/
    NULL,
    BOOLEAN,
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    BYTES,
    STRING;

    val code = this.name.toLowerCase()
}
