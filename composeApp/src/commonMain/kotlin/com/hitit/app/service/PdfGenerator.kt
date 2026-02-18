package com.hitit.app.service

import com.hitit.app.model.CardSet

/**
 * Result of PDF generation
 */
sealed class PdfResult {
    data class Success(val filePath: String) : PdfResult()
    data class Error(val message: String) : PdfResult()
}

/**
 * Platform-specific PDF generator for card sets.
 * Generates printable PDFs with QR code fronts and info backs.
 *
 * Card layout:
 * - Card size: 66mm x 66mm (matches original Hitster cards)
 * - Layout: 9 cards per A4/Letter page (3x3 grid)
 * - Front: QR code encoding Deezer track URL
 * - Back: Title, Artist, Year
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class PdfGenerator {
    /**
     * Generate a PDF for the given card set
     * @param cardSet The card set to generate PDF for
     * @param onProgress Callback for progress updates (0.0 to 1.0)
     * @return PdfResult with file path on success or error message on failure
     */
    suspend fun generatePdf(cardSet: CardSet, onProgress: (Float) -> Unit = {}): PdfResult

    /**
     * Share the generated PDF using system share sheet
     * @param filePath Path to the PDF file
     */
    suspend fun sharePdf(filePath: String)
}
