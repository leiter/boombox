package com.hitit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitit.app.model.CardSet
import com.hitit.app.model.CustomCard
import com.hitit.app.service.CardSetStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CardSetEditUiState(
    val cardSet: CardSet? = null,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val error: String? = null,
    val editedName: String = "",
    val editedDescription: String = ""
)

class CardSetEditViewModel(
    private val cardSetStore: CardSetStore
) : ViewModel() {
    private val _uiState = MutableStateFlow(CardSetEditUiState())
    val uiState: StateFlow<CardSetEditUiState> = _uiState.asStateFlow()

    private var cardSetId: String? = null

    fun loadCardSet(cardSetId: String) {
        this.cardSetId = cardSetId
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val cardSet = cardSetStore.loadCardSet(cardSetId)
                if (cardSet != null) {
                    _uiState.value = _uiState.value.copy(
                        cardSet = cardSet,
                        editedName = cardSet.name,
                        editedDescription = cardSet.description ?: "",
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Card set not found"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load card set"
                )
            }
        }
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(editedName = name)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(editedDescription = description)
    }

    fun saveChanges() {
        val cardSet = _uiState.value.cardSet ?: return
        val name = _uiState.value.editedName.trim()
        if (name.isEmpty()) {
            _uiState.value = _uiState.value.copy(error = "Name cannot be empty")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true, error = null)
            try {
                val description = _uiState.value.editedDescription.trim().ifEmpty { null }
                val updatedCardSet = cardSet.updateInfo(name, description)
                cardSetStore.saveCardSet(updatedCardSet)
                _uiState.value = _uiState.value.copy(
                    cardSet = updatedCardSet,
                    isSaving = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    error = e.message ?: "Failed to save changes"
                )
            }
        }
    }

    fun removeCard(cardId: String) {
        val cardSet = _uiState.value.cardSet ?: return
        viewModelScope.launch {
            try {
                val updatedCardSet = cardSet.removeCard(cardId)
                cardSetStore.saveCardSet(updatedCardSet)
                _uiState.value = _uiState.value.copy(cardSet = updatedCardSet)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to remove card"
                )
            }
        }
    }

    fun moveCard(fromIndex: Int, toIndex: Int) {
        val cardSet = _uiState.value.cardSet ?: return
        viewModelScope.launch {
            try {
                val updatedCardSet = cardSet.moveCard(fromIndex, toIndex)
                cardSetStore.saveCardSet(updatedCardSet)
                _uiState.value = _uiState.value.copy(cardSet = updatedCardSet)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to reorder cards"
                )
            }
        }
    }

    fun addCard(card: CustomCard) {
        val cardSet = _uiState.value.cardSet ?: return
        viewModelScope.launch {
            try {
                val updatedCardSet = cardSet.addCard(card)
                cardSetStore.saveCardSet(updatedCardSet)
                _uiState.value = _uiState.value.copy(cardSet = updatedCardSet)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to add card"
                )
            }
        }
    }

    fun updateCard(card: CustomCard) {
        val cardSet = _uiState.value.cardSet ?: return
        viewModelScope.launch {
            try {
                val updatedCardSet = cardSet.updateCard(card)
                cardSetStore.saveCardSet(updatedCardSet)
                _uiState.value = _uiState.value.copy(cardSet = updatedCardSet)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to update card"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
