package dev.barryzeha.plugins

import dev.barryzeha.data.repository.VideoGameRepository
import dev.barryzeha.data.repository.VideoGameRepositoryImpl
import dev.barryzeha.routes.gameRouting
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList


fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("\uD83D\uDC4B Hello Barry Reactive API REST!")
        }
        gameRouting()
    }

}
