plugins {
    `java-library`
}

description = "Immutable collections converters for Klaxon."

checkstyle {
    configFile = file("${rootProject.rootDir}/config/checkstyle/checkstyle.xml")
}

dependencies {
    implementation(project(":core"))
    implementation(
        group = "com.google.guava",
        name = "guava",
        version = property("guava.version") as String
    )
    implementation(group = "com.beust", name = "klaxon", version = "5.0.5")

    testImplementation(group = "com.natpryce", name = "hamkrest", version = "1.4.2.2")
}
