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

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.essentialsx.net/snapshots/")
    maven("https://jitpack.io/")

    flatDir {
        dirs("libs")
    }
}

dependencies {
    compileOnly(":spigot-1.18.1-R0.1-SNAPSHOT")

    compileOnly("net.essentialsx:EssentialsX:2.19.3-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
}

tasks.processResources {
    val props = hashMapOf("version" to version)
    filteringCharset = "UTF-8"

    filesMatching("plugin.yml") {
        expand(props)
    }
}
