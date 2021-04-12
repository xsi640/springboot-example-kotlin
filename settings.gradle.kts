rootProject.name = "springboot-example-kotlin"

fun defineSubProject(name: String, subdir: String = "") {
    val prjName = "springboot-$name"
    include(prjName)
    if (subdir.isEmpty()) {
        mkdir(prjName)
        project(":$prjName").projectDir = file(prjName)
    } else {
        val dir = "$subdir/$prjName"
        mkdir(dir)
        project(":$prjName").projectDir = file(dir)
    }
}

defineSubProject("jdbc", "")
defineSubProject("jpa", "")
defineSubProject("immutables-jpa", "")
defineSubProject("aop", "")
defineSubProject("async", "")
defineSubProject("commandline", "")
defineSubProject("interceptor", "")
defineSubProject("invocation-interface", "")