package dev.barryzeha.routes

import dev.barryzeha.data.repository.VideoGameRepository
import dev.barryzeha.data.repository.VideoGameRepositoryImpl
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.coroutines.flow.toList
import kotlin.text.toIntOrNull

private const val ENDPOINT = "api/games"
/*fun Application.gameRouting() {
    val games: VideoGameRepository = VideoGameRepositoryImpl()
    routing {
        route("/$ENDPOINT") {
            get {
                // QueryParams ??
                val page = call.request.queryParameters["page"]?.toIntOrNull()
                val perPage = call.request.queryParameters["perPage"]?.toIntOrNull() ?: 4

                if (page != null && page > 0) {
                    games.findAllPageable(page - 1, perPage).toList().run { call.respond(HttpStatusCode.OK, this) }

                } else {
                    games.findAll().toList()
                        .run { call.respond(HttpStatusCode.OK, this) }
                }
            }
        }
    }
}*/

fun Route.gameRouting(){
    val games: VideoGameRepository = VideoGameRepositoryImpl()
    route("/$ENDPOINT") {
        get {
            // QueryParams ??
            val page = call.request.queryParameters["page"]?.toIntOrNull()
            val perPage = call.request.queryParameters["perPage"]?.toIntOrNull() ?: 4

            if (page != null && page > 0) {
                games.findAllPageable(page - 1, perPage).toList().run { call.respond(HttpStatusCode.OK, this) }

            } else {
                games.findAll().toList()
                    .run { call.respond(HttpStatusCode.OK, this) }
            }
        }
        get("{id}"){
            val id=call.parameters["id"]?.toLongOrNull()
            id?.let{
                games.findById(it)?.run{
                    call.respond(HttpStatusCode.OK,this)
                } ?: call.respond(HttpStatusCode.NotFound, "Game not found with ID $id")
            } ?: call.respond(HttpStatusCode.BadRequest, "ID is not a number")
        }
    }
}