package dev.barryzeha.data.repository

import com.toxicbakery.bcrypt.Bcrypt
import dev.barryzeha.data.database.MDatabase
import dev.barryzeha.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.bytestring.encodeToByteString

class UserRepositoryImpl: UserRepository {
    val database = MDatabase.getInstance()
    override suspend fun saveUser(user: User): User? = withContext(Dispatchers.IO){
        val userWithPasswdEncrypt = user.copy(
            password = Bcrypt.hash(user.password,12).decodeToString()
        )
        database.saveUser(userWithPasswdEncrypt)
    }

    override suspend fun getAllUsers(): List<User>? = withContext(Dispatchers.IO){
        database.getAllUsers()
    }

    override suspend fun getUserById(id: String): User?  = withContext(Dispatchers.IO){
       database.getUserById(id)
    }

    override suspend fun getUserByUsername(name: String): User? = withContext(Dispatchers.IO){
        database.getUserByUsername(name)
    }
    override suspend fun checkUserNameAndPassword(username:String, password:String):User? = withContext(Dispatchers.IO){
        val user = database.getUserByUsername(username)
        return@withContext user?.let{
            if(Bcrypt.verify(password,user.password.encodeToByteArray())){
                return@withContext  user
            }
            return@withContext null
        }
    }
}