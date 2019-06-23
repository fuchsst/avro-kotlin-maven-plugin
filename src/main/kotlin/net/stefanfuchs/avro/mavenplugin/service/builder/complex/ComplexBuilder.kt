package net.stefanfuchs.avro.mavenplugin.service.builder.complex

interface ComplexBuilder {
    val packageName: String
    val className: String
    val filename: String

    fun build(): String

   override fun equals(other: Any?): Boolean
}
