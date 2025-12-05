package com.hitit.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val id: String,
    val title: String? = null,
    val artist: String? = null,
    val year: Int? = null,
    val albumCoverUrl: String? = null,
    val serviceType: MusicServiceType = MusicServiceType.DEEZER
)

enum class MusicServiceType {
    DEEZER,
    SPOTIFY,
    YOUTUBE
}
