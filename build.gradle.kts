plugins {
    java
    application
}

group = "me.giverplay"
version = "3.3.8"

application {
    applicationDefaultJvmArgs = listOf("-Dfile.encondig=utf-8")
}

repositories {
    mavenCentral()

    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://repo.essentialsx.net/snapshots/")
    maven("https://jitpack.io/")

    flatDir {
        dirs("libs")
    }
}

dependencies {
    compileOnly(":paper-1.18.2")

    compileOnly("net.essentialsx:EssentialsX:2.20.0-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
}

tasks.processResources {
    val props = hashMapOf("version" to version)
    filteringCharset = "UTF-8"

    filesMatching("plugin.yml") {
        expand(props)
    }
}
