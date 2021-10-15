package io.samjingwen.codegenerator;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

public abstract class CodeGeneratorPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    if (!project.getPluginManager().hasPlugin("java")) {
      throw new IllegalStateException("Java plugin is required!");
    }

    var sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
    var mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);

    var buildDir = project.getBuildDir().getPath();
    mainSourceSet.getJava().srcDir(buildDir + "/generated/codegen/src/main/java");

    var tasks = project.getTasks();
    var codeGenerationTasks = tasks.withType(CodeGenerationTask.class);

    tasks
        .named("compileJava")
        .configure(compileJavaTask -> compileJavaTask.dependsOn(codeGenerationTasks));
  }
}
