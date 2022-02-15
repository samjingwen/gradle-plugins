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
    repositories{
        mavenCentral()
    }
}

plugins {
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

tasks.asciidoctor {
    sourceDir(file("docs"))
    setOutputDir(file("build/docs"))
}

