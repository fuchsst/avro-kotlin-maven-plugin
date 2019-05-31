package net.stefanfuchs.avro.mavenplugin

import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugins.annotations.Mojo

@Mojo(name = "schema")
class PluginRunner : AbstractMojo() {


    @Throws(MojoExecutionException::class)
    override fun execute() {

    }
}
