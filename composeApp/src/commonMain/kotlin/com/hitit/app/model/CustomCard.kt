package com.hitit.app.model

import com.benasher44.uuid.uuid4
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

/**
 * Represents a custom card created by the user from a Deezer track.
 * Can be used in custom card sets for gameplay.
 */
@Serializable
data class CustomCard(
    val id: String = uuid4().toString(),
    val deezerId: Long,
    val title: String,
    val artist: String,
    val year: Int?,
    val albumTitle: String? = null,
    val albumCoverUrl: String? = null,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds()
) {
    /**
     * Converts this custom card to a Track for playback
     */
    fun toTrack(): Track {
        return Track(
            id = deezerId.toString(),
            title = title,
            artist = artist,
            year = year,
            albumCoverUrl = albumCoverUrl,
            serviceType = MusicServiceType.DEEZER
        )
    }
}
