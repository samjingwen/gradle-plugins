import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm") version "1.6.10"
    id("com.gradle.plugin-publish") version "0.20.0"
    id("com.diffplug.spotless") version "6.2.2"
}

group = "io.samjingwen"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.yaml:snakeyaml:1.29")
    implementation("com.github.jknack:handlebars:4.3.0")

    testImplementation(kotlin("test"))
}

tasks.withType<KotlinCompile> {
    dependsOn(tasks.spotlessApply)
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

spotless {
    kotlin {
        ktlint()
    }
}

gradlePlugin {
    plugins {
        create("codeGeneratorPlugin") {
            id = "io.samjingwen.code-generator"
            displayName = "Code Generator"
            description = "Gradle plugin to generate java code from handlebars template"
            implementationClass = "io.samjingwen.codegenerator.CodeGeneratorPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/samjingwen/gradle-plugins"
    vcsUrl = "https://github.com/samjingwen/gradle-plugins"
    tags = listOf("java", "code", "mustache", "handlebars", "generator")
}