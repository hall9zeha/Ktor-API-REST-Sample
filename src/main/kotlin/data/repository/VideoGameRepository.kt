package dev.barryzeha.data.repository

import dev.barryzeha.model.VideoGame
import io.ktor.http.ContentType
import kotlinx.coroutines.flow.Flow

interface VideoGameRepository {
    suspend fun findAll(): Flow<VideoGame>
    suspend fun findById(id:Long): VideoGame?
    suspend fun findAllPageable(page:Int, perPage:Int): Flow<VideoGame>
    suspend fun findByDeveloper(developer:String): Flow<VideoGame>
    suspend fun save(entity: VideoGame): VideoGame
    fun update(entity: VideoGame): VideoGame
    suspend fun create(entity: VideoGame): VideoGame
    suspend fun delete(entity: VideoGame): VideoGame?
    suspend fun deleteAll()
    suspend fun saveAll(entities: Iterable<VideoGame>): Flow<VideoGame>
}