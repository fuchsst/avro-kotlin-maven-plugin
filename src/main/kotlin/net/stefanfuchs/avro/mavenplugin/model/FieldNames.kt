package net.stefanfuchs.avro.mavenplugin.model

enum class FieldNames {
    TYPE,
    NAME,
    ALIASES,
    FIELDS,
    NAMESPACE,
    DOC,
    SORTORDER,
    DEFAULT,
    SYMBOLS,
    ITEMS,
    VALUES,
    SIZE;

    val code = this.name.toLowerCase()
}
