package dev.barryzeha.routes

import dev.barryzeha.data.repository.UserRepository
import dev.barryzeha.data.repository.UserRepositoryImpl
import dev.barryzeha.model.User
import dev.barryzeha.model.UserLoginDto
import dev.barryzeha.model.UserWithToken
import dev.barryzeha.services.TokenService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val USER_ENDPOINT = "api/v1/users"
fun Route.userRouting(){
    val userRepo: UserRepository = UserRepositoryImpl()
    val tokenService = TokenService.getInstance()
    route("/$USER_ENDPOINT"){
        //We can wrap any request that we want to authenticate with our token
        // to restrict the requests only if we have a valid token.
       authenticate {
            get {
                val userId = call.principal<JWTPrincipal>()
                    ?.payload?.getClaim("userId")?.asString().toString().replace("\"", "")

                print(userId)
                if(userId.isNotEmpty()){
                userRepo.getAllUsers()?.toList()
                    .run {
                        call.respond(HttpStatusCode.OK, this!!)
                    }
                }
            }
       }
        post("/register"){
            val user = call.receive<User>()
            userRepo.saveUser(user).run{
                this?.let{
                    call.respond(HttpStatusCode.Created, this)
                }
            }
        }
        post("/login"){
            val userLogin = call.receive<UserLoginDto>()
            val user = userRepo.checkUserNameAndPassword(userLogin.username!!,userLogin.password!!)
            user?.let{u->
                val token = tokenService.generateJWT(user)
                call.respond(HttpStatusCode.OK, UserWithToken(u.username,token))
            }?:run{
                call.respond(HttpStatusCode.NotFound,"User or password incorrect")
            }
        }
        get("/id/{userId}"){
            val id = call.parameters["userId"]
            id?.let{
                userRepo.getUserById(id)?.run{
                    call.respond(HttpStatusCode.OK,this)
                }?:call.respond(HttpStatusCode.NotFound,"User not found by userId")

            }?:call.respond(HttpStatusCode.BadRequest,"userId is not a String")

        }
        get("/username/{userName}"){
            val name = call.parameters["userName"]
            name?.let{
                userRepo.getUserByUsername(it)?.run{
                    call.respond(HttpStatusCode.OK,this)
                }?:call.respond(HttpStatusCode.NotFound,"User not found by userName")

            }?:call.respond(HttpStatusCode.BadRequest,"userName is not a String")

        }
    }
}