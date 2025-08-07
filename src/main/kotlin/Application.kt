package dev.barryzeha

import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigMergeable
import dev.barryzeha.data.repository.VideoGameRepository
import dev.barryzeha.data.repository.VideoGameRepositoryImpl
import dev.barryzeha.plugins.configSecurity
import dev.barryzeha.plugins.configureRouting
import dev.barryzeha.plugins.configureSerialization
import dev.barryzeha.routes.gameRouting
import io.ktor.server.application.*



fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    configSecurity()
    configureSerialization()
    configureRouting()
}
