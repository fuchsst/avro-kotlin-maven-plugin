package net.stefanfuchs.avro.mavenplugin.service

import net.stefanfuchs.avro.mavenplugin.service.builder.complex.ComplexBuilder
import java.io.File

object CodeGenerator {

    fun writeKotlinCodeFile(basePath: String, builder: ComplexBuilder): File {
        return with(File(basePath + builder.filename)) {
            this.writeText(builder.build())
            return@with this
        }
    }

    fun writeKotlinCodeFiles(basePath: String, builderSet: Set<ComplexBuilder>): Set<File> {
        return builderSet
                .map { writeKotlinCodeFile(basePath, it) }
                .toSet()
    }


}
