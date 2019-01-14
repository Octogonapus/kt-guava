plugins {
    `java-library`
}

description = "Kotlin Guava collections utilities."

checkstyle {
    configFile = file("${rootProject.rootDir}/config/checkstyle/checkstyle.xml")
}

dependencies {
    api(group = "com.google.guava", name = "guava", version = "27.0.1-jre")

    testImplementation(group = "com.natpryce", name = "hamkrest", version = "1.4.2.2")
}
