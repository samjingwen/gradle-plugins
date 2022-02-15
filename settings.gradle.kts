rootProject.name = "gradle-plugins"

includeBuild("./plugins/code-generator")
includeBuild("./plugins/spring-aspect-ctw")

include(":examples:code-generator-example")
include(":examples:spring-aspect-ctw-example")