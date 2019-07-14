package net.stefanfuchs.avro.mavenplugin.service.builder.types.complex

internal interface ComplexBuilder {
    val packageName: String
    val className: String
    val filepath: String
    val filename: String
    val fullFilename: String

    fun build(): String

    override fun equals(other: Any?): Boolean
}
