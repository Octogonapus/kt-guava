plugins {
    `java-library`
}

description = "Immutable collections converters for Klaxon."

checkstyle {
    configFile = file("${rootProject.rootDir}/config/checkstyle/checkstyle.xml")
}

dependencies {
    api(project(":core"))
    api(
        group = "com.google.guava",
        name = "guava",
        version = property("guava.version") as String
    )

    implementation(
        group = "com.beust",
        name = "klaxon",
        version = property("klaxon.version") as String
    )

    testImplementation(
        group = "com.natpryce",
        name = "hamkrest",
        version = property("hamkrest.version") as String
    )
}
