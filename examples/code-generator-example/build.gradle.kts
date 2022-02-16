import io.samjingwen.codegenerator.CodeGenerationTask

plugins {
    java
    id("io.samjingwen.code-generator")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation("org.slf4j:slf4j-simple:1.7.32")
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
}

sourceSets {
    main {
        java {
            srcDir("$buildDir/generated/codegen")
        }
    }
}

tasks.withType<JavaCompile> {
    dependsOn(tasks.withType(CodeGenerationTask::class.java))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

generateCode {
    templateFile.set("$projectDir/src/main/resources/templates/example.hbs")
    sourceFile.set("$projectDir/src/main/resources/source.yaml")
    outputFile.set("$buildDir/generated/codegen/HttpStatus.java")
}

