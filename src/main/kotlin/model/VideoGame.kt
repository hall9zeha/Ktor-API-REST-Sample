package dev.barryzeha.model

import java.time.LocalDate
import java.time.LocalDateTime

data class VideoGame(
    val id: Long = NEW_GAME,
    val title: String,
    val developer: String,
    val publisher: String,
    val genre: String,
    val platform: List<String>, // Ej: "PC", "PS5", "Xbox", "Switch"
    val releaseDate: LocalDate,
    val price: Double,
    val rating: Double = 0.0, // Valoración promedio de usuarios
    val coverImage: String = DEFAULT_COVER,
    val description: String = "",
    val isMultiplayer: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val isDeleted: Boolean = false
) {
    companion object {
        const val DEFAULT_COVER = "https://example.com/default_game_cover.jpg"
        const val NEW_GAME = -1L
    }
}
