package dev.barryzeha.routes

import dev.barryzeha.data.repository.UserRepository
import dev.barryzeha.data.repository.UserRepositoryImpl
import dev.barryzeha.model.User
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.coroutines.flow.toList

const val USER_ENDPOINT = "api/users"
fun Route.userRouting(){
    val userRepo: UserRepository = UserRepositoryImpl()
    route("/$USER_ENDPOINT"){
        get{
            userRepo.getAllUsers()?.toList()
                .run { call.respond(HttpStatusCode.OK, this!!) }
        }
        post{
            val user = call.receive<User>()
            userRepo.saveUser(user).run{
                this?.let{
                    call.respond(HttpStatusCode.Created, this)
                }
            }
        }
    }
}