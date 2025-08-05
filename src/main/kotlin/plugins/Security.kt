package dev.barryzeha.plugins

import dev.barryzeha.services.TokenException
import dev.barryzeha.services.TokenService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configSecurity(){
    val jwtService = TokenService.getInstance()

    authentication {
        jwt {
            verifier(jwtService.verifyJWT())
            realm=jwtService.realm
            validate { credential ->
                // If the token is valid, it also has the indicated audience,
                // and has the user's field to compare it with the one we want
                // return the JWTPrincipal, otherwise return null
                if (credential.payload.audience.contains(jwtService.audience) &&
                    credential.payload.getClaim("username").asString().isNotEmpty()
                ) {
                    JWTPrincipal(credential.payload)
                }
                else null
            }

            challenge {defaultScheme, realm->
                throw TokenException.InvalidTokenException("Invalid or expired token")
            }
        }
    }
}