package com.hitit.app.service

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.hitit.app.model.CardSet
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CardSetStore(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )
    private val json = Json { ignoreUnknownKeys = true }

    actual fun saveCardSet(cardSet: CardSet) {
        val cardSetJson = json.encodeToString(cardSet)
        prefs.edit { putString(keyForCardSet(cardSet.id), cardSetJson) }
        // Update the index of card set IDs
        val ids = getCardSetIds().toMutableSet()
        ids.add(cardSet.id)
        prefs.edit { putStringSet(KEY_CARD_SET_IDS, ids) }
    }

    actual fun loadAllCardSets(): List<CardSet> {
        return getCardSetIds().mapNotNull { id ->
            loadCardSet(id)
        }.sortedByDescending { it.updatedAt }
    }

    actual fun loadCardSet(cardSetId: String): CardSet? {
        val cardSetJson = prefs.getString(keyForCardSet(cardSetId), null) ?: return null
        return try {
            json.decodeFromString<CardSet>(cardSetJson)
        } catch (e: Exception) {
            null
        }
    }

    actual fun deleteCardSet(cardSetId: String) {
        prefs.edit { remove(keyForCardSet(cardSetId)) }
        val ids = getCardSetIds().toMutableSet()
        ids.remove(cardSetId)
        prefs.edit { putStringSet(KEY_CARD_SET_IDS, ids) }
    }

    actual fun clearAll() {
        val ids = getCardSetIds()
        prefs.edit {
            ids.forEach { id ->
                remove(keyForCardSet(id))
            }
            remove(KEY_CARD_SET_IDS)
        }
    }

    private fun getCardSetIds(): Set<String> {
        return prefs.getStringSet(KEY_CARD_SET_IDS, emptySet()) ?: emptySet()
    }

    private fun keyForCardSet(id: String): String = "${KEY_PREFIX_CARD_SET}$id"

    companion object {
        private const val PREFS_NAME = "card_sets"
        private const val KEY_CARD_SET_IDS = "card_set_ids"
        private const val KEY_PREFIX_CARD_SET = "card_set_"
    }
}
