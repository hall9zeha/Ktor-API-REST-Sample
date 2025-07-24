package dev.barryzeha.model

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
@Serializable
data class VideoGame(
    val id: Long = NEW_GAME,
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
        const val NEW_GAME = -1L
        fun now(): String = java.time.LocalDateTime.now().toString()
    }
}
