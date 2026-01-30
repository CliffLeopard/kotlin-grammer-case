plugins {
    kotlin("jvm") version "2.2.20"
}

group = "com.transsion"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))
    testImplementation(kotlin("test"))
    // 样本代码用到的协程 / 反射
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.2.20")
}

tasks.test {
    useJUnitPlatform()
}