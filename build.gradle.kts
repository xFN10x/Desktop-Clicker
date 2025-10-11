plugins {
    application
    id("com.gradleup.shadow") version "9.2.2"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.guava)

    implementation("com.google.code.gson:gson:2.13.2")
    implementation("com.formdev:flatlaf:3.6.1")
    implementation("commons-io:commons-io:2.19.0")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "fn10.desktopClicker.Launcher"
}

val version = "1.1.0"

tasks.shadowJar {
  archiveBaseName = "DesktopClicker"
  archiveVersion = version
  destinationDirectory = layout.buildDirectory.dir("builtJars")
}