package net.stefanfuchs.avro.mavenplugin

import net.stefanfuchs.avro.mavenplugin.service.CodeGenerator
import net.stefanfuchs.avro.mavenplugin.service.builder.Builder
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter

@Mojo(name = "schema")
class PluginRunner : AbstractMojo() {

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
        val builder = Builder()
        builder.readSchemas(sourcePath, extensionFilter)
        includeFileList.forEach { builder.readSchema(PluginRunner::class.java.getResource(it).openStream()) }
        CodeGenerator.writeKotlinCodeFiles(destinationPath, builder.buildComplexBuilderList())
    }
}
