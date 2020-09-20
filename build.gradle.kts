plugins {
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
    kotlin("kapt") version "1.4.0"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "com.github.CLJeremy"
version = "0.1-SNAPSHOT"

repositories {
    mavenLocal()
    jcenter()
    mavenCentral()
}

kotlin.sourceSets.all {
    languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
}

val miraiCoreVersion = "1.3.0"
val miraiConsoleVersion = "1.0-M4"

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))

    compileOnly("net.mamoe:mirai-core:$miraiCoreVersion")
    compileOnly("net.mamoe:mirai-console:$miraiConsoleVersion")

    val autoService = "1.0-rc7"
    kapt("com.google.auto.service", "auto-service", autoService)
    compileOnly("com.google.auto.service", "auto-service-annotations", autoService)

    testImplementation("net.mamoe:mirai-console:$miraiConsoleVersion")
    testImplementation("net.mamoe:mirai-core:$miraiCoreVersion")
    testImplementation("net.mamoe:mirai-console-pure:$miraiConsoleVersion")
    testImplementation(kotlin("stdlib-jdk8"))
}

kotlin.target.compilations.all {
    kotlinOptions.freeCompilerArgs += "-Xjvm-default=enable"
    kotlinOptions.jvmTarget = "1.8"
}