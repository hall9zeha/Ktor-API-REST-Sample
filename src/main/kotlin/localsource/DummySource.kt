package dev.barryzeha.localsource

import dev.barryzeha.model.VideoGame
import java.time.LocalDate

class DummySource {


        val games = mutableListOf(
            VideoGame(
                id = 1,
                title = "Huntdown",
                developer = "Easy Trigger Games",
                publisher = "Coffee Stain Publishing",
                genre = "Action / Run and Gun",
                platform = listOf("PC", "PS4", "Xbox One", "Switch"),
                releaseDate = LocalDate.of(2020, 5, 12),
                price = 19.99,
                rating = 8.5,
                coverImage = "https://cdn.cloudflare.steamstatic.com/steam/apps/598550/header.jpg",
                description = "Retro-style arcade shooter with intense action and humor.",
                isMultiplayer = false
            ),
            VideoGame(
                id = 2,
                title = "Ori and the Blind Forest",
                developer = "Moon Studios",
                publisher = "Microsoft Studios",
                genre = "Platformer / Adventure",
                platform = listOf("PC", "Xbox One", "Switch"),
                releaseDate = LocalDate.of(2015, 3, 11),
                price = 14.99,
                rating = 9.2,
                coverImage = "https://cdn.cloudflare.steamstatic.com/steam/apps/261570/header.jpg",
                description = "Emotional and beautifully crafted platformer.",
                isMultiplayer = false
            ),
            VideoGame(
                id = 3,
                title = "Mass Effect 2",
                developer = "BioWare",
                publisher = "Electronic Arts",
                genre = "Action RPG / Sci-Fi",
                platform = listOf("PC", "Xbox 360", "PS3", "Legendary Edition (PS4/PC/Xbox One)"),
                releaseDate = LocalDate.of(2010, 1, 26),
                price = 29.99,
                rating = 9.5,
                coverImage = "https://cdn.cloudflare.steamstatic.com/steam/apps/24980/header.jpg",
                description = "Sci-fi RPG with deep narrative and memorable characters.",
                isMultiplayer = false
            ),
            VideoGame(
                id = 4,
                title = "Halo: Combat Evolved",
                developer = "Bungie",
                publisher = "Microsoft Game Studios",
                genre = "First-Person Shooter",
                platform = listOf("Xbox", "PC", "Xbox Series X|S (via MCC)"),
                releaseDate = LocalDate.of(2001, 11, 15),
                price = 9.99,
                rating = 9.0,
                coverImage = "https://upload.wikimedia.org/wikipedia/en/0/02/Halo_-_Combat_Evolved_(XBox_version_-_box_art).jpg",
                description = "Classic sci-fi FPS that redefined console shooters.",
                isMultiplayer = true
            ),
            VideoGame(
                id = 5,
                title = "DOOM Eternal",
                developer = "id Software",
                publisher = "Bethesda Softworks",
                genre = "First-Person Shooter",
                platform = listOf("PC", "PS4", "PS5", "Xbox One", "Xbox Series X|S", "Switch"),
                releaseDate = LocalDate.of(2020, 3, 20),
                price = 39.99,
                rating = 9.3,
                coverImage = "https://cdn.cloudflare.steamstatic.com/steam/apps/782330/header.jpg",
                description = "Brutal and fast-paced demon-slaying action.",
                isMultiplayer = true
            ),
            VideoGame(
                id = 6,
                title = "Earthion",
                developer = "Ancient Corporation",
                publisher = "Superdeluxe Games",
                genre = "Shoot 'em up",
                platform = listOf("PC", "PS4", "PS5", "Xbox Series X|S", "Switch", "Mega Drive"),
                releaseDate = LocalDate.of(2025, 7, 31),
                price = 19.99,
                rating = 0.0, // Aún sin valoraciones oficiales
                coverImage = "https://cdn.cloudflare.steamstatic.com/steam/apps/2968200/header.jpg", // imagen de Steam
                description = "Retro-style shmup designed for modern systems and original Mega Drive hardware.",
                isMultiplayer = false
            )
        )


}