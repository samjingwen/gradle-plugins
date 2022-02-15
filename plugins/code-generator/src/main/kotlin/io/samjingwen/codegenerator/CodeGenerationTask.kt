package io.samjingwen.codegenerator

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.io.FileTemplateLoader
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import org.yaml.snakeyaml.Yaml
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter

@CacheableTask
abstract class CodeGenerationTask : DefaultTask() {
    @get:Input
    abstract val templateFile: Property<String>

    @get:Input
    abstract val sourceFile: Property<String>

    @get:Input
    abstract val outputFile: Property<String>

    @TaskAction
    fun generate() {
        val sourceSets = project.extensions.getByType(SourceSetContainer::class.java)
        val mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)

        val resources = mainSourceSet.resources
        val resourceDir = resources.sourceDirectories.asPath

        val inputStream = FileInputStream(File(resourceDir, sourceFile.get()))
        inputStream.use {
            val context: Map<String, Any> = Yaml().load(inputStream)
            val buildDir = project.buildDir.path

            val output = File("$buildDir/generated/codegen")
            output.mkdirs()

            val loader = FileTemplateLoader(resourceDir)
            val handlebars = Handlebars(loader)
            val template = handlebars.compile(templateFile.get())
            val content = template.apply(context)
            writeFile(File(output, outputFile.get()), content)
        }
    }
}

private fun writeFile(destination: File, content: String) {
    BufferedWriter(FileWriter(destination)).use { it.write(content) }
}
