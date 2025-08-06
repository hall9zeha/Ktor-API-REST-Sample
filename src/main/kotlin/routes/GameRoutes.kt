package dev.barryzeha.routes

import dev.barryzeha.data.repository.VideoGameRepository
import dev.barryzeha.data.repository.VideoGameRepositoryImpl
import dev.barryzeha.model.VideoGame
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.coroutines.flow.toList
import kotlin.text.toIntOrNull

private const val ENDPOINT = "api/v1/games"
fun Route.gameRouting(){
    val games: VideoGameRepository = VideoGameRepositoryImpl()
    route("/$ENDPOINT") {
        // Get all video games
        get {
            // QueryParams ??
            val page = call.request.queryParameters["page"]?.toIntOrNull()
            val perPage = call.request.queryParameters["perPage"]?.toIntOrNull() ?: 4

            if (page != null && page > 0) {
                games.findAllPageable(page, perPage).toList().run { call.respond(HttpStatusCode.OK, this) }

            } else {
                games.findAll().toList()
                    .run { call.respond(HttpStatusCode.OK, this) }
            }
        }
        // Get by id
        get("{id}"){
            val id=call.parameters["id"]
            id?.let{
                games.findById(it)?.run{
                    call.respond(HttpStatusCode.OK,this)
                } ?: call.respond(HttpStatusCode.NotFound, "Game not found with ID $id")
            } ?: call.respond(HttpStatusCode.BadRequest, "ID is not a number")
        }
        // Get by developer
        get("/developer/{developer}"){
            val developer = call.parameters["developer"]
            developer?.let{
                games.findByDeveloper(developer)?.run {
                    call.respond(HttpStatusCode.OK,this)
                }?: call.respond(HttpStatusCode.NotFound,"Game not found by $developer")
            }?: call.respond(HttpStatusCode.BadRequest,"Developer is not a string")
        }
        // Create a new video game
        post {
            val game = call.receive<VideoGame>()
            games.save(game).run {
                this?.let {
                    call.respond(HttpStatusCode.Created, this)
                }
            }
        }
        // Update by id
        // the body also requires an ID
        put("update/{id}"){
            val id = call.parameters["id"]
            id?.let{
               val game = call.receive<VideoGame>()
                games.findById(it)?.let{response->
                    response?.let {
                        games.save(game)
                            .run { call.respond(HttpStatusCode.OK, response!!) }
                    }
                }?:call.respond(HttpStatusCode.NotFound, "Game not found with ID $id")
            }?: call.respond(HttpStatusCode.BadRequest, "ID is not a number")
        }

        // Delete by id
        delete("delete/{id}"){
            val id = call.parameters["id"]
            id?.let{
                games.findById(it)?.let{game->
                    games.delete(game)
                        .run { call.respond(HttpStatusCode.NoContent) }
                }?:call.respond(HttpStatusCode.NotFound,"Game not found with $id")
            }?:call.respond(HttpStatusCode.BadRequest,"Id is not a number")
        }
    }
}