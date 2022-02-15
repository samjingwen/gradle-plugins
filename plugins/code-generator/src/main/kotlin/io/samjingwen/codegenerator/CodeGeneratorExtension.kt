package io.samjingwen.codegenerator

import org.gradle.api.provider.Property

abstract class CodeGeneratorExtension {
    abstract val templateFile: Property<String>
    abstract val sourceFile: Property<String>
    abstract val outputFile: Property<String>
}
