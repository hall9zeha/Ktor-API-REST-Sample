package dev.barryzeha.model

import dev.barryzeha.common.ObjectIdSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.*
import org.bson.codecs.pojo.annotations.BsonId

import org.bson.types.ObjectId
import java.time.LocalDate
import java.time.LocalDateTime
@Serializable
data class VideoGame(
    @SerialName("_id")

    // @Contextual val id: ObjectId? = null; The approach suggested in the documentation does not work correctly
    // for deserializing with kotlinx.serialization
    // You need to create your own serializer
    @Serializable(with = ObjectIdSerializer::class)
    val id: ObjectId?= null,
    val title: String,
    val developer: String,
    val publisher: String,
    val genre: String,
    val platform: List<String>, // Ej: "PC", "PS5", "Xbox", "Switch"
    val releaseDate: String,
    val price: Double,
    val rating: Double = 0.0, // Valoración promedio de usuarios
    val coverImage: String = DEFAULT_COVER,
    val description: String = "",
    val isMultiplayer: Boolean = false,
    val createdAt: String = now(),
    val updatedAt: String = now(),
    val isDeleted: Boolean = false
) {
    companion object {
        const val DEFAULT_COVER = "https://example.com/default_game_cover.jpg"
        fun now(): String = java.time.LocalDateTime.now().toString()
    }
}
