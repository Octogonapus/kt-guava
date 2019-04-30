plugins {
    `java-library`
}

description = "Guava collections utilities for Kotlin."

checkstyle {
    configFile = file("${rootProject.rootDir}/config/checkstyle/checkstyle.xml")
}

dependencies {
    implementation(group = "com.google.guava", name = "guava", version = "27.0.1-jre")
    implementation(group = "com.beust", name = "klaxon", version = "5.0.5")

    testImplementation(group = "com.natpryce", name = "hamkrest", version = "1.4.2.2")
}
