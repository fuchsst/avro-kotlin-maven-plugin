package net.stefanfuchs.avro.mavenplugin.service

import net.stefanfuchs.avro.mavenplugin.service.builder.complex.ComplexBuilder
import org.slf4j.LoggerFactory
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


internal object CodeGenerator {

    val logger = LoggerFactory.getLogger(CodeGenerator::class.java)

    fun writeKotlinCodeFile(basePath: String, builder: ComplexBuilder): File {
        val path: Path = Paths.get(basePath + builder.filepath)
        if (Files.notExists(path)) {
            logger.info("Creating path $path")
            Files.createDirectories(path)
        }

        return with(File(basePath + builder.fullFilename)) {
            logger.info("Writing ${this.absolutePath}")
            val sourceCode: String = builder.build()
            logger.debug(sourceCode)
            with(FileWriter(this)) {
                with(BufferedWriter(this)) {
                    writeText(sourceCode)
                    flush()
                }
            }
            this
        }

    }

    fun writeKotlinCodeFiles(basePath: String, builderSet: Set<ComplexBuilder>): Set<File> {
        logger.info("Writing ${builderSet.size} Kotlin Classes to '$basePath':")
        return builderSet
                .map { writeKotlinCodeFile(basePath, it) }
                .toSet()
    }


}
