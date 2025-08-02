package dev.barryzeha.data.repository

import dev.barryzeha.data.database.MDatabase
import dev.barryzeha.localsource.DummySource
import dev.barryzeha.model.VideoGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class VideoGameRepositoryImpl: VideoGameRepository {
    private val database = MDatabase.getInstance()
    private val dummySource = DummySource()

    override suspend fun findAll(): Flow<VideoGame> = withContext(Dispatchers.IO){
        database.getAllGames().asFlow()
    }

    override suspend fun findById(id: String): VideoGame?= withContext(Dispatchers.IO){
        database.findById(id)
    }

    override suspend fun findAllPageable(
        page: Int,
        perPage: Int
    ): Flow<VideoGame> = withContext(Dispatchers.IO)  {
        database.getGamesWithPagination(page,perPage)!!.asFlow()
    }

    override suspend fun findByDeveloper(developer: String): Flow<VideoGame> = withContext(Dispatchers.IO) {
        database.findByDeveloper(developer)!!.asFlow()
    }

    override suspend fun save(entity: VideoGame): VideoGame = withContext(Dispatchers.IO) {
        if (entity.id == null) {
            create(entity)
        } else {
            update(entity)
        }
    }

    override suspend fun update(entity: VideoGame): VideoGame {
        val videoGameUpdate = entity.copy(updatedAt = LocalDateTime.now().toString())
        database.updateGame(videoGameUpdate)
        return entity
    }

    override suspend fun create(entity: VideoGame): VideoGame {
         val newGame = entity.copy(
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )
        database.saveGame(newGame)
        return newGame
    }

    override suspend fun delete(entity: VideoGame): VideoGame? = withContext(Dispatchers.IO){
        database.deleteGame(entity)
        entity
    }

    override suspend fun deleteAll() {
        dummySource.games.clear()
    }

    override suspend fun saveAll(entities: Iterable<VideoGame>): Flow<VideoGame> = withContext(Dispatchers.IO){
        entities.forEach { save(it) }
        entities.asFlow()
    }
}