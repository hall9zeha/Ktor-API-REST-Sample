package dev.barryzeha.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginDto (
    val username:String="",
    val password:String=""
)

@Serializable
data class UserWithToken(
    val username:String="",
    val token:String=""
)