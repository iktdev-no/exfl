plugins {
    kotlin("jvm") version "1.7.10"
    id("maven-publish")
}

val name = "exfl"
group = "no.iktdev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.10")


    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    test {
        useJUnitPlatform()
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}


val reposiliteUrl = if (version.toString().endsWith("SNAPSHOT")) {
    "https://reposilite.iktdev.no/snapshots"
} else {
    "https://reposilite.iktdev.no/releases"
}

publishing {
    publications {
        create<MavenPublication>("reposilite") {
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set(name)
                description.set("Extend Functionality Library")
                version = project.version.toString()
                url.set(reposiliteUrl)
            }
            from(components["kotlin"])
        }
    }
    repositories {
        maven {
            name = name
            url = uri(reposiliteUrl)
            credentials {
                username = System.getenv("reposiliteUsername")
                password = System.getenv("reposilitePassword")
            }
        }
    }
}