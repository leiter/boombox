package com.hitit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitit.app.model.CardSet
import com.hitit.app.service.CardSetStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CardSetListUiState(
    val cardSets: List<CardSet> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class CardSetListViewModel(
    private val cardSetStore: CardSetStore
) : ViewModel() {
    private val _uiState = MutableStateFlow(CardSetListUiState())
    val uiState: StateFlow<CardSetListUiState> = _uiState.asStateFlow()

    init {
        loadCardSets()
    }

    fun loadCardSets() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val cardSets = cardSetStore.loadAllCardSets()
                _uiState.value = _uiState.value.copy(
                    cardSets = cardSets,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load card sets"
                )
            }
        }
    }

    fun deleteCardSet(cardSetId: String) {
        viewModelScope.launch {
            try {
                cardSetStore.deleteCardSet(cardSetId)
                loadCardSets()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete card set"
                )
            }
        }
    }
}
