package dev.barryzeha.data.database

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.typesafe.config.ConfigFactory
import dev.barryzeha.model.VideoGame
import io.ktor.http.ContentType
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList
import org.bson.Document
import org.bson.types.ObjectId

class MDatabase {
    private var mongoClient: MongoClient?=null
    private var database: MongoDatabase?=null
    private var collection: MongoCollection<VideoGame>?=null
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
        collection = database?.getCollection<VideoGame>("game")
    }
    suspend fun saveGame(game: VideoGame):VideoGame?{
        val result =  collection?.insertOne(game)
        val documentId=result?.insertedId
        return collection?.find(Filters.eq("_id",documentId))?.firstOrNull()
    }
    suspend fun getAllGames():List<VideoGame>{
        return collection?.find()?.toList()?:emptyList()
    }
    suspend fun getGamesWithPagination(page:Int, perPage:Int):List<VideoGame>?{
        val skipAmount = (page - 1) * perPage
        return collection?.find()?.skip(skipAmount)?.limit(perPage)?.toList()?:emptyList()
    }
    suspend fun findById(id: String): VideoGame?{
        val objectId = ObjectId(id)
        return collection?.find(Filters.eq("_id",objectId))?.firstOrNull()
    }
    suspend fun findByDeveloper(developer:String): List<VideoGame>?{
        val filter = Filters.eq("developer", developer)
        return collection?.find(filter)?.toList()
    }
    suspend fun updateGame(game: VideoGame){
        val filter = Filters.eq("_id",game.id)
        collection?.replaceOne(filter,game)
    }
    suspend fun deleteGame(game: VideoGame){
        val filter = Filters.eq("_id", game.id)
        collection?.deleteOne(filter)
    }
}