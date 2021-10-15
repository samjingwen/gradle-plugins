package io.samjingwen.codegenerator;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;
import org.yaml.snakeyaml.Yaml;

@CacheableTask
public abstract class CodeGenerationTask extends DefaultTask {
  @Input
  abstract Property<String> getTemplateFile();

  @Input
  abstract Property<String> getSourceFile();

  @Input
  abstract Property<String> getOutputFile();

  @TaskAction
  public void generate() {
    SourceSetContainer sourceSets =
        getProject().getExtensions().getByType(SourceSetContainer.class);
    SourceSet mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
    SourceDirectorySet resources = mainSourceSet.getResources();
    String resourceDir = resources.getSourceDirectories().getAsPath();

    try (InputStream inputStream =
        new FileInputStream(new File(resourceDir, getSourceFile().get()))) {
      Map<String, Object> context = new Yaml().load(inputStream);

      var buildDir = getProject().getBuildDir().getPath();

      File output = new File(buildDir + "/generated/codegen/src/main/java/");
      output.mkdirs();

      TemplateLoader loader = new FileTemplateLoader(resourceDir);
      Handlebars handlebars = new Handlebars(loader);
      Template template = handlebars.compile(getTemplateFile().get());
      String content = template.apply(context);
      writeFile(new File(output, getOutputFile().get()), content);

    } catch (IOException | ClassCastException e) {
      e.printStackTrace();
      throw new GradleException("Unable to generate code", e);
    }
  }

  private void writeFile(File destination, String content) throws IOException {
    try (BufferedWriter output = new BufferedWriter(new FileWriter(destination))) {
      output.write(content);
    }
  }
}
