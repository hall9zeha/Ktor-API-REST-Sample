package dev.barryzeha.data.repository

import dev.barryzeha.localsource.DummySource
import dev.barryzeha.model.VideoGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class VideoGameRepositoryImpl: VideoGameRepository {
    private val dummySource = DummySource()

    override suspend fun findAll(): Flow<VideoGame> = withContext(Dispatchers.IO){
        dummySource.games.asFlow()
    }

    override suspend fun findById(id: Long): VideoGame?= withContext(Dispatchers.IO){
        dummySource.games.find { game -> game.id==id }
    }

    override suspend fun findAllPageable(
        page: Int,
        perPage: Int
    ): Flow<VideoGame> = withContext(Dispatchers.IO)  {
        val myLimit = if (perPage > 100) 100L else perPage.toLong()
        val myOffset = (page * perPage).toLong()
        dummySource.games.subList(myOffset.toInt(), myLimit.toInt()).asFlow()
    }

    override suspend fun findByDeveloper(developer: String): Flow<VideoGame> = withContext(Dispatchers.IO) {
        dummySource.games.filter { it.developer.contains(developer,true) }
            .asFlow()
    }

    override suspend fun save(entity: VideoGame): VideoGame = withContext(Dispatchers.IO) {
        if (entity.id == VideoGame.NEW_GAME) {
            create(entity)
        } else {
            update(entity)
        }
    }

    override fun update(entity: VideoGame): VideoGame {
        val index = dummySource.games.indexOfFirst { it.id== entity.id }
         dummySource.games[index] = entity.copy(updatedAt = LocalDateTime.now().toString())
        return entity
    }

    override fun create(entity: VideoGame): VideoGame {
        val id = dummySource.games.maxOfOrNull { it.id }?.plus(1) ?: 1L
        val newGame = entity.copy(
            id = id,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )
        dummySource.games.add(newGame)
        return newGame
    }

    override suspend fun delete(entity: VideoGame): VideoGame? = withContext(Dispatchers.IO){
        dummySource.games.remove(entity)
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