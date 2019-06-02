package net.stefanfuchs.avro.mavenplugin.model.input

enum class FieldNames(val code: String) {
    TYPE("type"),
    NAME("name"),
    ALIASES("aliases"),
    FIELDS("fields"),
    NAMESPACE("namespace"),
    DOC("doc"),
    SORTORDER("sortorder"),
    DEFAULT("default"),
    SYMBOLS("symbols"),
    ITEMS("items"),
    VALUES("values"),
    SIZE("size"),
    LOGICALTYPE("logicalType"),
    PRECISION("precision"),
    SCALE("scale");
}
