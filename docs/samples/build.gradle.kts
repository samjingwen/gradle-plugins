// tag::plugins[]
plugins {
    id("io.samjingwen.code-generator")
}
// end::plugins[]

// tag::sourcesets[]
sourceSets {
    main {
        java {
            srcDir("$buildDir/generated/codegen")
        }
    }
}
// end::sourcesets[]

// tag::dependencies[]
tasks.withType<JavaCompile> {
    dependsOn(tasks.withType(io.samjingwen.codegenerator.CodeGenerationTask::class.java))
}
// end::dependencies[]

// tag::configuration[]
generateCode {
    templateFile.set("$projectDir/src/main/resources/templates/example.hbs")
    sourceFile.set("$projectDir/src/main/resources/source.yaml")
    outputFile.set("$buildDir/generated/codegen/HttpStatus.java")
}
// end::configuration[]
