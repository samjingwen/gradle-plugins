package io.samjingwen.codegenerator

import org.gradle.api.Plugin
import org.gradle.api.Project

class CodeGeneratorPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("generateCode", CodeGeneratorExtension::class.java)
        project.tasks.register("generateCode", CodeGenerationTask::class.java) {
            it.templateFile.set(extension.templateFile)
            it.sourceFile.set(extension.sourceFile)
            it.outputFile.set(extension.outputFile)
        }
    }
}
