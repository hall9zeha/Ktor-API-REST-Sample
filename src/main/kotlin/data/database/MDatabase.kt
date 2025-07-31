package dev.barryzeha.data.database

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.typesafe.config.ConfigFactory
import dev.barryzeha.model.VideoGame

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
    suspend fun saveGame(game: VideoGame){
        collection?.insertOne(game)
    }
}