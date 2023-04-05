plugins {
    application
    java
}

application {
    mainClass.set("com.mbi.Application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.telegram:telegrambots:6.5.0")
    implementation("ch.qos.logback:logback-classic:1.4.6")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.mbi.Application"
    }
}

tasks.register("stage") {
    dependsOn("clean", "installDist")
}
