package com.hitit.app.service

import com.hitit.app.model.CardSet
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CardSetStore {
    private val defaults = NSUserDefaults.standardUserDefaults
    private val json = Json { ignoreUnknownKeys = true }

    actual fun saveCardSet(cardSet: CardSet) {
        val cardSetJson = json.encodeToString(cardSet)
        defaults.setObject(cardSetJson, keyForCardSet(cardSet.id))
        // Update the index of card set IDs
        val ids = getCardSetIds().toMutableList()
        if (!ids.contains(cardSet.id)) {
            ids.add(cardSet.id)
        }
        defaults.setObject(ids, KEY_CARD_SET_IDS)
    }

    actual fun loadAllCardSets(): List<CardSet> {
        return getCardSetIds().mapNotNull { id ->
            loadCardSet(id)
        }.sortedByDescending { it.updatedAt }
    }

    actual fun loadCardSet(cardSetId: String): CardSet? {
        val cardSetJson = defaults.stringForKey(keyForCardSet(cardSetId)) ?: return null
        return try {
            json.decodeFromString<CardSet>(cardSetJson)
        } catch (e: Exception) {
            null
        }
    }

    actual fun deleteCardSet(cardSetId: String) {
        defaults.removeObjectForKey(keyForCardSet(cardSetId))
        val ids = getCardSetIds().toMutableList()
        ids.remove(cardSetId)
        defaults.setObject(ids, KEY_CARD_SET_IDS)
    }

    actual fun clearAll() {
        val ids = getCardSetIds()
        ids.forEach { id ->
            defaults.removeObjectForKey(keyForCardSet(id))
        }
        defaults.removeObjectForKey(KEY_CARD_SET_IDS)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getCardSetIds(): List<String> {
        return (defaults.arrayForKey(KEY_CARD_SET_IDS) as? List<String>) ?: emptyList()
    }

    private fun keyForCardSet(id: String): String = "${KEY_PREFIX_CARD_SET}$id"

    companion object {
        private const val KEY_CARD_SET_IDS = "card_set_ids"
        private const val KEY_PREFIX_CARD_SET = "card_set_"
    }
}
