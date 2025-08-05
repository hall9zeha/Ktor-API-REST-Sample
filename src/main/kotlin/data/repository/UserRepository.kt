package dev.barryzeha.data.repository

import dev.barryzeha.model.User

interface UserRepository {
    suspend fun saveUser(user: User): User?
    suspend fun getAllUsers():List<User>?
    suspend fun getUserById(id:String):User?
    suspend fun getUserByUsername(name:String):User?
    suspend fun checkUserNameAndPassword(username:String, password:String):User?
}