package dev.barryzeha.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import dev.barryzeha.model.User
import io.ktor.server.config.ApplicationConfig
import java.util.Date

sealed class TokenException(message:String):RuntimeException(message){
    class InvalidTokenException(message: String): TokenException(message)
}

class TokenService(
    private val mConfig: ApplicationConfig
){
    companion object {
        private var instance: TokenService?=null
        fun getInstance(): TokenService{
            return instance?: synchronized(this){TokenService(ApplicationConfig("application.conf")).also { instance=it }}
        }
    }
    val audience by lazy {
        mConfig.propertyOrNull("jwt.audience")?.getString() ?: "jwt-audience"
    }
    val realm by lazy {
        mConfig.propertyOrNull("jwt.realm")?.getString() ?: "jwt-realm"
    }
    private val issuer by lazy {
        mConfig.propertyOrNull("jwt.issuer")?.getString() ?: "jwt-issuer"
    }
    private val expiresIn by lazy {
        mConfig.propertyOrNull("jwt.expiration")?.getString()?.toLong() ?: 3600
    }
    private val secret by lazy {
        mConfig.propertyOrNull("jwt.secret")?.getString() ?: "jwt-secret"
    }
    fun decodeJWT(token: String): DecodedJWT {
        return JWT.decode(token)
    }
    fun printClaims(token: String) {
        val decodedJWT = decodeJWT(token)

        val userId = decodedJWT.getClaim("userId").asString()
        val username = decodedJWT.getClaim("username").asString()
        val usermail = decodedJWT.getClaim("usermail").asString()

        println("Decoded JWT Claims:")
        println("userId: $userId")
        println("username: $username")
        println("usermail: $usermail")
    }

    fun generateJWT(user: User):String{
        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withSubject("Authentication")
            // user claims and other data to store
            .withClaim("username", user.username)
            .withClaim("usermail", user.email)
            .withClaim("userId", user.id.toString())
            // expiration time from currentTimeMillis + (tiempo times in seconds) * 1000 (to millis)
            .withExpiresAt(Date(System.currentTimeMillis() + expiresIn * 1000L))
            // sign with secret
            .sign(
                Algorithm.HMAC512(secret)
            )

        return token
    }
    fun verifyJWT(): JWTVerifier {

        return try {
            JWT.require(Algorithm.HMAC512(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build()
        } catch (e: Exception) {
            throw TokenException.InvalidTokenException("Invalid token")
        }
    }
}