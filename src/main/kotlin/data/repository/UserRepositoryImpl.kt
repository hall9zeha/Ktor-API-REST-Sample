package dev.barryzeha.data.repository

import dev.barryzeha.data.database.MDatabase
import dev.barryzeha.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl: UserRepository {
    val database = MDatabase.getInstance()
    override suspend fun saveUser(user: User): User? = withContext(Dispatchers.IO){
        database.saveUser(user)
    }

    override suspend fun getAllUsers(): List<User>? = withContext(Dispatchers.IO){
        database.getAllUsers()
    }
}