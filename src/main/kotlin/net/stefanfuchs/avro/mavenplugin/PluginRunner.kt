package net.stefanfuchs.avro.mavenplugin

import net.stefanfuchs.avro.mavenplugin.service.CodeGenerator
import net.stefanfuchs.avro.mavenplugin.service.builder.Builder
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.slf4j.LoggerFactory
import java.io.File

@Mojo(name = "schema")
class PluginRunner : AbstractMojo() {

    val logger = LoggerFactory.getLogger(PluginRunner::class.java)

    @Parameter
    val includeFileList: List<String> = emptyList()
    @Parameter
    val extensionFilter: List<String> = listOf(".avsc", ".avro")
    @Parameter(defaultValue = "/avro/")
    val sourcePath: String = "/avro/"
    @Parameter(defaultValue = "./generated-sources")
    val destinationPath = "./generated-sources"


    @Throws(MojoExecutionException::class)
    override fun execute() {

        logger.info("Avro-Kotlin Code generation...")
        val builder = Builder()

        builder.readSchemas(sourcePath, extensionFilter)
        logger.info("Adding schemas from include list: $includeFileList")
        includeFileList.forEach { builder.readSchema(File(it).inputStream()) }
        CodeGenerator.writeKotlinCodeFiles(destinationPath, builder.buildComplexBuilderList())
    }
}
