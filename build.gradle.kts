import com.google.protobuf.gradle.*

plugins {
    id("java")
    kotlin("jvm") version "1.6.0"
    id("com.google.protobuf") version "0.8.18"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation("io.grpc:grpc-kotlin-stub:1.2.0")
    implementation("io.grpc:grpc-protobuf:1.40.0")
    implementation("io.grpc:grpc-netty:1.40.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.19.6")
    implementation("com.google.protobuf:protobuf-java:3.19.6")
    implementation("com.google.protobuf:protobuf-gradle-plugin:0.9.3")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.0.0"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.39.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}


tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
application {
    mainClass.set("MainKt")
}
