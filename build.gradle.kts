buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.samjingwen:code-generator:1.0")
        classpath("io.samjingwen:spring-aspect-ctw:1.0")
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

tasks.asciidoctor {
    sourceDir(file("docs/asciidoc"))
    setOutputDir(file("build/docs"))
    attributes(
        mapOf(
            "toc" to "left",
            "source-highlighter" to "prettify",
            "icons" to "font",
            "numbered" to "",
            "idprefix" to "",
            "docinfo1" to "true",
            "sectanchors" to "true",
            "samplesCodeDir" to "docs/samples",
            "gitRepoUri" to "https://github.com/samjingwen/gradle-plugins"
        )
    )
}

