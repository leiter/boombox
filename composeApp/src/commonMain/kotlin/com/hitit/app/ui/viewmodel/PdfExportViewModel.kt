package com.hitit.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitit.app.model.CardSet
import com.hitit.app.service.CardSetStore
import com.hitit.app.service.PdfGenerator
import com.hitit.app.service.PdfResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PdfExportUiState(
    val cardSet: CardSet? = null,
    val isLoading: Boolean = true,
    val isGenerating: Boolean = false,
    val error: String? = null,
    val generatedPdfPath: String? = null,
    val progress: Float = 0f
)

class PdfExportViewModel(
    private val cardSetStore: CardSetStore,
    private val pdfGenerator: PdfGenerator
) : ViewModel() {
    private val _uiState = MutableStateFlow(PdfExportUiState())
    val uiState: StateFlow<PdfExportUiState> = _uiState.asStateFlow()

    fun loadCardSet(cardSetId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val cardSet = cardSetStore.loadCardSet(cardSetId)
                if (cardSet != null) {
                    _uiState.value = _uiState.value.copy(
                        cardSet = cardSet,
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

    fun generatePdf() {
        val cardSet = _uiState.value.cardSet ?: return
        if (cardSet.cards.isEmpty()) {
            _uiState.value = _uiState.value.copy(error = "Card set has no cards")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isGenerating = true,
                error = null,
                progress = 0f,
                generatedPdfPath = null
            )
            try {
                val result = pdfGenerator.generatePdf(cardSet) { progress ->
                    _uiState.value = _uiState.value.copy(progress = progress)
                }
                when (result) {
                    is PdfResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isGenerating = false,
                            generatedPdfPath = result.filePath,
                            progress = 1f
                        )
                    }
                    is PdfResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isGenerating = false,
                            error = result.message
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    error = e.message ?: "Failed to generate PDF"
                )
            }
        }
    }

    fun sharePdf() {
        val path = _uiState.value.generatedPdfPath ?: return
        viewModelScope.launch {
            try {
                pdfGenerator.sharePdf(path)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to share PDF"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
