package com.hitit.app.service

import com.hitit.app.model.CardSet
import com.hitit.app.model.CustomCard
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.CoreGraphics.*
import platform.Foundation.*
import platform.UIKit.*

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual class PdfGenerator {

    companion object {
        // A4 dimensions in points (72 points = 1 inch)
        private const val PAGE_WIDTH = 595.0
        private const val PAGE_HEIGHT = 842.0

        // Card dimensions (66mm = ~187 points at 72 dpi)
        private const val CARD_SIZE_MM = 66.0
        private const val MM_TO_POINTS = 2.835 // 72 points / 25.4 mm
        private val CARD_SIZE = (CARD_SIZE_MM * MM_TO_POINTS) // ~187 points

        // Grid layout
        private const val CARDS_PER_ROW = 3
        private const val CARDS_PER_PAGE = 9

        // Margins
        private const val MARGIN_TOP = 40.0
        private const val MARGIN_LEFT = 20.0
        private const val CARD_SPACING = 10.0

        // QR code size
        private val QR_SIZE = CARD_SIZE - 30.0
        private const val QR_PADDING = 15.0
    }

    actual suspend fun generatePdf(cardSet: CardSet, onProgress: (Float) -> Unit): PdfResult {
        return withContext(Dispatchers.Main) {
            try {
                val cards = cardSet.cards
                val totalCards = cards.size
                val totalFrontPages = (totalCards + CARDS_PER_PAGE - 1) / CARDS_PER_PAGE

                val pdfData = NSMutableData()
                val pageRect = CGRectMake(0.0, 0.0, PAGE_WIDTH, PAGE_HEIGHT)

                UIGraphicsBeginPDFContextToData(pdfData, pageRect, null)

                // Generate front pages (QR codes)
                for (pageIndex in 0 until totalFrontPages) {
                    val startIndex = pageIndex * CARDS_PER_PAGE
                    val endIndex = minOf(startIndex + CARDS_PER_PAGE, totalCards)
                    val pageCards = cards.subList(startIndex, endIndex)

                    UIGraphicsBeginPDFPage()
                    val context = UIGraphicsGetCurrentContext()
                    if (context != null) {
                        drawFrontPage(context, pageCards)
                    }

                    val frontProgress = (pageIndex + 1).toFloat() / (totalFrontPages * 2)
                    onProgress(frontProgress)
                }

                // Generate back pages (info)
                for (pageIndex in 0 until totalFrontPages) {
                    val startIndex = pageIndex * CARDS_PER_PAGE
                    val endIndex = minOf(startIndex + CARDS_PER_PAGE, totalCards)
                    val pageCards = cards.subList(startIndex, endIndex)

                    UIGraphicsBeginPDFPage()
                    val context = UIGraphicsGetCurrentContext()
                    if (context != null) {
                        drawBackPage(context, pageCards)
                    }

                    val backProgress = 0.5f + (pageIndex + 1).toFloat() / (totalFrontPages * 2)
                    onProgress(backProgress)
                }

                UIGraphicsEndPDFContext()

                // Save to file
                val fileName = "${cardSet.name.replace(Regex("[^a-zA-Z0-9]"), "_")}_cards.pdf"
                val cacheDir = NSSearchPathForDirectoriesInDomains(
                    NSCachesDirectory,
                    NSUserDomainMask,
                    true
                ).firstOrNull() as? String ?: return@withContext PdfResult.Error("Cannot access cache directory")

                val filePath = "$cacheDir/$fileName"
                val success = pdfData.writeToFile(filePath, atomically = true)

                if (success) {
                    onProgress(1f)
                    PdfResult.Success(filePath)
                } else {
                    PdfResult.Error("Failed to write PDF file")
                }
            } catch (e: Exception) {
                PdfResult.Error(e.message ?: "Failed to generate PDF")
            }
        }
    }

    private fun drawFrontPage(context: CGContextRef, cards: List<CustomCard>) {
        // Set up stroke for card borders
        CGContextSetStrokeColorWithColor(context, UIColor.blackColor.CGColor)
        CGContextSetLineWidth(context, 1.0)

        cards.forEachIndexed { index, card ->
            val row = index / CARDS_PER_ROW
            val col = index % CARDS_PER_ROW
            val x = MARGIN_LEFT + col * (CARD_SIZE + CARD_SPACING)
            // Flip Y coordinate for iOS coordinate system
            val y = PAGE_HEIGHT - MARGIN_TOP - (row + 1) * CARD_SIZE - row * CARD_SPACING

            // Draw card border
            CGContextStrokeRect(context, CGRectMake(x, y, CARD_SIZE, CARD_SIZE))

            // Draw QR code placeholder text (actual QR generation is complex in K/N)
            // The URL that would be encoded
            val url = "deezer.com/track/${card.deezerId}"
            drawCenteredText(
                context,
                text = url,
                x = x + CARD_SIZE / 2,
                y = y + CARD_SIZE / 2,
                fontSize = 10.0
            )
        }
    }

    private fun drawBackPage(context: CGContextRef, cards: List<CustomCard>) {
        // Set up stroke for card borders
        CGContextSetStrokeColorWithColor(context, UIColor.blackColor.CGColor)
        CGContextSetLineWidth(context, 1.0)

        cards.forEachIndexed { index, card ->
            val row = index / CARDS_PER_ROW
            val col = index % CARDS_PER_ROW
            // Mirror column for back side
            val mirroredCol = CARDS_PER_ROW - 1 - col
            val x = MARGIN_LEFT + mirroredCol * (CARD_SIZE + CARD_SPACING)
            val y = PAGE_HEIGHT - MARGIN_TOP - (row + 1) * CARD_SIZE - row * CARD_SPACING

            // Draw card border
            CGContextStrokeRect(context, CGRectMake(x, y, CARD_SIZE, CARD_SIZE))

            // Draw title
            drawCenteredText(
                context,
                text = truncateText(card.title, 20),
                x = x + CARD_SIZE / 2,
                y = y + CARD_SIZE - 50.0,
                fontSize = 14.0,
                bold = true
            )

            // Draw artist
            drawCenteredText(
                context,
                text = truncateText(card.artist, 25),
                x = x + CARD_SIZE / 2,
                y = y + CARD_SIZE - 75.0,
                fontSize = 12.0
            )

            // Draw year
            card.year?.let { year ->
                drawCenteredText(
                    context,
                    text = year.toString(),
                    x = x + CARD_SIZE / 2,
                    y = y + 50.0,
                    fontSize = 24.0,
                    bold = true
                )
            }
        }
    }

    private fun drawCenteredText(
        context: CGContextRef,
        text: String,
        x: Double,
        y: Double,
        fontSize: Double,
        bold: Boolean = false
    ) {
        val font = if (bold) {
            UIFont.boldSystemFontOfSize(fontSize)
        } else {
            UIFont.systemFontOfSize(fontSize)
        }

        val attributes = mapOf<Any?, Any?>(
            NSFontAttributeName to font,
            NSForegroundColorAttributeName to UIColor.blackColor
        )

        val nsString = NSString.create(string = text)
        val size = nsString.sizeWithAttributes(attributes)

        size.useContents {
            val textX = x - width / 2
            val textY = y - height / 2
            nsString.drawAtPoint(CGPointMake(textX, textY), withAttributes = attributes)
        }
    }

    private fun truncateText(text: String, maxLength: Int): String {
        return if (text.length <= maxLength) text
        else text.take(maxLength - 3) + "..."
    }

    actual suspend fun sharePdf(filePath: String) {
        withContext(Dispatchers.Main) {
            val fileUrl = NSURL.fileURLWithPath(filePath)
            val activityViewController = UIActivityViewController(
                activityItems = listOf(fileUrl),
                applicationActivities = null
            )

            val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            rootViewController?.presentViewController(
                activityViewController,
                animated = true,
                completion = null
            )
        }
    }
}
