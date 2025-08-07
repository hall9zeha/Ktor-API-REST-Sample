val kotlin_version: String by project
val logback_version: String by project
val ktor_version:String by project
val bcrypt_version:String by project

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.2.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
}

group = "dev.barryzeha"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
    //mainClass.set("ktor-apirest-sample.ApplicationKt")
}

// Para probar ./gradlew buildFatJar de forma local podemos forzar la versión de java que queremos que utilice para
// la compilación. Ya que tenemos un dockerfile para que ejecute la versión de java que especifiquemos allí y lo despliegue
// en nuestro servidor en la nube ya no necesitamos esto y lo comentamos
//
/*kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11)) // o usa 11 si prefieres
    }
}*/

ktor{
    // Para personalizar el nombre de archivo .jar creado al ejecutar ./gradlew buildFatJar
    fatJar {
        archiveFileName.set("ktor-sample-api-rest.jar")
    }
}
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // Mongo db
    implementation(platform("org.mongodb:mongodb-driver-bom:5.5.1"))
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine")
    implementation("org.mongodb:bson-kotlinx")

    // Auth JWT
    implementation("io.ktor:ktor-server-auth-jvm:${ktor_version}")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:${ktor_version}")
    implementation("io.ktor:ktor-server-host-common-jvm:${ktor_version}")
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:${bcrypt_version}")

    // SSL/TLS
    implementation("io.ktor:ktor-network-tls-certificates:${ktor_version}")
}
