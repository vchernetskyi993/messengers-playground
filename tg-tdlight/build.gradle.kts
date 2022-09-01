import java.net.URI

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

repositories {
    mavenCentral()
    maven { url = URI("https://mvn.mchv.eu/repository/mchv/") }
}

application {
    mainClass.set("com.example.MainKt")
}

dependencies {
    implementation(platform("it.tdlight:tdlight-java-bom:2.8.4.1"))
    implementation("it.tdlight:tdlight-java")
    implementation("it.tdlight:tdlight-natives-linux-amd64")

    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-core:1.2.11")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.11")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.3")
    implementation("io.projectreactor:reactor-core:3.4.19")

    implementation("com.typesafe:config:1.4.2")

    implementation("io.ktor:ktor-client-core:2.1.0")
    implementation("io.ktor:ktor-client-cio:2.1.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.0")
}
