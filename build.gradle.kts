plugins {
    id("java-library")
    id ("maven-publish")
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

    // for vaultAPI
    maven("https://jitpack.io")

    // idk
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.maven.apache.org/maven2/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
}

tasks {
    processResources {
        val props = mapOf("version" to version, "description" to project.description)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}

// Custom task:
//      Build the project normally and copy the .jar file to the
//      test server's plugins folder.
tasks.register<Copy>("buildToTestServer") {
    group = "build"
    dependsOn(tasks.build)

    from(tasks.jar.flatMap { it.archiveFile })
    into("D:/MC_testserver/plugins")
}