package dev.barryzeha.data.repository

import dev.barryzeha.model.User

interface UserRepository {
    suspend fun saveUser(user: User): User?
    suspend fun getAllUsers():List<User>?
}