plugins {
    `java-library`
}

description = "Guava collections utilities for Kotlin."

checkstyle {
    configFile = file("${rootProject.rootDir}/config/checkstyle/checkstyle.xml")
}

dependencies {
    implementation(
        group = "com.google.guava",
        name = "guava",
        version = property("guava.version") as String
    )

    testImplementation(group = "com.natpryce", name = "hamkrest", version = "1.4.2.2")
}
