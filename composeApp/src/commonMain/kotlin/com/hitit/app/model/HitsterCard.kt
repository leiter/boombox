package com.hitit.app.model

import kotlinx.serialization.Serializable

@Serializable
data class HitsterCard(
    val hitsterId: String,
    val title: String,
    val artist: String,
    val year: Int,
    val deezerId: Long? = null,
    val deezerTitle: String? = null,
    val deezerArtist: String? = null,
    val deezerAlbum: String? = null
) {
    fun toTrack(): Track? {
        return deezerId?.let {
            Track(
                id = it.toString(),
                title = deezerTitle ?: title,
                artist = deezerArtist ?: artist,
                year = year,
                serviceType = MusicServiceType.DEEZER
            )
        }
    }
}
