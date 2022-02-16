package io.samjingwen.codegenerator

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.io.FileTemplateLoader
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
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
        val inputStream = FileInputStream(File(sourceFile.get()))
        inputStream.use {
            val context: Map<String, Any> = Yaml().load(inputStream)

            val outputDir = File(outputFile.get())
            outputDir.parentFile.mkdirs()

            val templateDir = File(templateFile.get())
            val loader = FileTemplateLoader(templateDir.parent)
            val handlebars = Handlebars(loader)
            val template = handlebars.compile(templateDir.nameWithoutExtension)
            val content = template.apply(context)
            writeFile(outputDir, content)
        }
    }
}

private fun writeFile(destination: File, content: String) {
    BufferedWriter(FileWriter(destination)).use { it.write(content) }
}
