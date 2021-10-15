package io.samjingwen.springaspect

import org.gradle.api.GradleException
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class SpringAspectCTWPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        if (!project.hasProperty('sourceCompatibility')) {
            throw new GradleException("You must set the property 'sourceCompatibility' before applying the aspectj plugin")
        }
        if (!project.hasProperty('targetCompatibility')) {
            throw new GradleException("You must set the property 'targetCompatibility' before applying the aspectj plugin")
        }

        def plugins = project.getPlugins()
        plugins.apply('java')
        plugins.apply('org.springframework.boot')
        plugins.apply('io.spring.dependency-management')

        def ajc = project.configurations.maybeCreate('ajc')
        def aspects = project.configurations.maybeCreate('aspects')

        project.configurations.named("implementation") {
            it.extendsFrom(aspects)
        }

        project.dependencies {
            delegate.ajc "org.aspectj:aspectjtools:1.9.7"
            delegate.implementation "org.aspectj:aspectjrt:1.9.7"
            delegate.aspects "org.springframework:spring-aspects"
            delegate.aspects "org.springframework:spring-tx"
            delegate.aspects "org.springframework:spring-orm"
        }

        project.tasks.named("compileJava") {
            dependsOn project.configurations.ajc.getTaskDependencyFromProjectDependency(true, "compileJava")

            doLast {
                ant.taskdef(resource: "org/aspectj/tools/ant/taskdefs/aspectjTaskdefs.properties",
                        classpath: project.configurations.ajc.asPath)

                ant.iajc(
                        fork: "false",
                        showWeaveInfo: "true",
                        verbose: "true",
                        source: project.sourceCompatibility,
                        target: project.targetCompatibility,
                        failonerror: "true",
                        destDir: project.sourceSets.main.output.classesDirs.asPath,
                        aspectPath: project.configurations.aspects.asPath,
                        inpath: project.sourceSets.main.output.classesDirs.asPath,
                        classpath: project.sourceSets.main.runtimeClasspath.asPath,
                )
            }
        }
    }
}
