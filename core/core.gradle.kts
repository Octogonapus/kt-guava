plugins {
    `java-library`
}

description = "Guava collections utilities for Kotlin."

checkstyle {
    configFile = file("${rootProject.rootDir}/config/checkstyle/checkstyle.xml")
}

dependencies {
    api(
        group = "com.google.guava",
        name = "guava",
        version = property("guava.version") as String
    )

    testImplementation(
        group = "com.natpryce",
        name = "hamkrest",
        version = property("hamkrest.version") as String
    )
}
