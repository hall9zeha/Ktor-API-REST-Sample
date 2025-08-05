package dev.barryzeha.data.database

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.typesafe.config.ConfigFactory
import dev.barryzeha.model.User
import dev.barryzeha.model.VideoGame
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId

class MDatabase {
    private var mongoClient: MongoClient?=null
    private var database: MongoDatabase?=null
    private var gameCollection: MongoCollection<VideoGame>?=null
    private var userCollection: MongoCollection<User>?=null
    companion object {
        private var instance: MDatabase? = null
        fun getInstance(): MDatabase {
            if (instance == null) {
                instance = MDatabase()
            }
            return instance!!
        }
    }
    init {
        setupConnectionDB()
    }
    private fun setupConnectionDB() {
        val config = ConfigFactory.parseResources("local.conf").resolve()
        val mongoUri = config.getString("mongo.uri")
        mongoClient = MongoClient.create(mongoUri)
        database = mongoClient?.getDatabase("games_db")
        gameCollection = database?.getCollection<VideoGame>("game")
        userCollection = database?.getCollection<User>("user")
    }
    suspend fun saveGame(game: VideoGame):VideoGame?{
        val result =  gameCollection?.insertOne(game)
        val documentId=result?.insertedId
        return gameCollection?.find(Filters.eq("_id",documentId))?.firstOrNull()
    }
    suspend fun getAllGames():List<VideoGame>{
        return gameCollection?.find()?.toList()?:emptyList()
    }
    suspend fun getGamesWithPagination(page:Int, perPage:Int):List<VideoGame>?{
        val skipAmount = (page - 1) * perPage
        return gameCollection?.find()?.skip(skipAmount)?.limit(perPage)?.toList()?:emptyList()
    }
    suspend fun findById(id: String): VideoGame?{
        val objectId = ObjectId(id)
        return gameCollection?.find(Filters.eq("_id",objectId))?.firstOrNull()
    }
    suspend fun findByDeveloper(developer:String): List<VideoGame>?{
        val filter = Filters.eq("developer", developer)
        return gameCollection?.find(filter)?.toList()
    }
    suspend fun updateGame(game: VideoGame){
        val filter = Filters.eq("_id",game.id)
        gameCollection?.replaceOne(filter,game)
    }
    suspend fun deleteGame(game: VideoGame){
        val filter = Filters.eq("_id", game.id)
        gameCollection?.deleteOne(filter)
    }
    // User
    suspend fun saveUser(user: User):User?{
        val result = userCollection?.insertOne(user)
        val documentId = result?.insertedId
        return userCollection?.find(Filters.eq("_id",documentId))?.firstOrNull()
    }
    suspend fun getAllUsers():List<User>?{
        return userCollection?.find()?.toList()?:emptyList()
    }
    suspend fun getUserById(id:String):User?{
        return userCollection?.find(Filters.eq("_id",id))?.firstOrNull()
    }
    suspend fun getUserByUsername(username:String):User?{
        return userCollection?.find(Filters.eq("username",username))?.firstOrNull()
    }
}