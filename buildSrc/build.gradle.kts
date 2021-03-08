repositories {
    jcenter()
}

plugins {
    `kotlin-dsl`
}

configurations {
    implementation.get().exclude(mapOf("group" to "org.jetbrains", "module" to "annotations"))
}