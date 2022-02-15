plugins {
    `groovy-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.20.0"
}

group = "io.samjingwen"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.6.3")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
}

gradlePlugin {
    plugins {
        create("springAspectCTWPlugin") {
            id = "io.samjingwen.spring-aspect-ctw"
            displayName = "Spring Aspect Compile Time Weaving Plugin"
            description = "Gradle plugin for spring aspect compile time weaving"
            implementationClass = "io.samjingwen.springaspect.SpringAspectCTWPlugin"
        }
    }
}

pluginBundle {
    website = ("https://github.com/samjingwen/gradle-plugins")
    vcsUrl = "https://github.com/samjingwen/gradle-plugins"
    tags = listOf("spring", "aspectj", "compile", "time", "weaving")
}
