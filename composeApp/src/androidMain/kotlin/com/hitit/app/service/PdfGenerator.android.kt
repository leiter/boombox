package com.hitit.app.service

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import android.graphics.Typeface
import androidx.core.content.FileProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.hitit.app.model.CardSet
import com.hitit.app.model.CustomCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PdfGenerator(private val context: Context) {

    companion object {
        // A4 dimensions in points (72 points = 1 inch)
        private const val PAGE_WIDTH = 595 // A4 width in points
        private const val PAGE_HEIGHT = 842 // A4 height in points

        // Card dimensions (66mm = ~187 points at 72 dpi)
        private const val CARD_SIZE_MM = 66f
        private const val MM_TO_POINTS = 2.835f // 72 points / 25.4 mm
        private const val CARD_SIZE = (CARD_SIZE_MM * MM_TO_POINTS).toInt() // ~187 points

        // Grid layout
        private const val CARDS_PER_ROW = 3
        private const val CARDS_PER_PAGE = 9

        // Margins
        private const val MARGIN_TOP = 40
        private const val MARGIN_LEFT = 20
        private const val CARD_SPACING = 10

        // QR code size (slightly smaller than card for padding)
        private const val QR_SIZE = CARD_SIZE - 30
        private const val QR_PADDING = 15
    }

    actual suspend fun generatePdf(cardSet: CardSet, onProgress: (Float) -> Unit): PdfResult {
        return withContext(Dispatchers.IO) {
            try {
                val document = PdfDocument()
                val cards = cardSet.cards
                val totalCards = cards.size
                val totalFrontPages = (totalCards + CARDS_PER_PAGE - 1) / CARDS_PER_PAGE

                // Generate front pages (QR codes)
                for (pageIndex in 0 until totalFrontPages) {
                    val startIndex = pageIndex * CARDS_PER_PAGE
                    val endIndex = minOf(startIndex + CARDS_PER_PAGE, totalCards)
                    val pageCards = cards.subList(startIndex, endIndex)

                    val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageIndex * 2 + 1).create()
                    val page = document.startPage(pageInfo)
                    drawFrontPage(page.canvas, pageCards)
                    document.finishPage(page)

                    // Update progress (fronts are first half)
                    val frontProgress = (pageIndex + 1).toFloat() / (totalFrontPages * 2)
                    onProgress(frontProgress)
                }

                // Generate back pages (info) - in reverse order for double-sided printing
                for (pageIndex in 0 until totalFrontPages) {
                    val startIndex = pageIndex * CARDS_PER_PAGE
                    val endIndex = minOf(startIndex + CARDS_PER_PAGE, totalCards)
                    val pageCards = cards.subList(startIndex, endIndex)

                    val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageIndex * 2 + 2).create()
                    val page = document.startPage(pageInfo)
                    drawBackPage(page.canvas, pageCards)
                    document.finishPage(page)

                    // Update progress (backs are second half)
                    val backProgress = 0.5f + (pageIndex + 1).toFloat() / (totalFrontPages * 2)
                    onProgress(backProgress)
                }

                // Save to file
                val fileName = "${cardSet.name.replace(Regex("[^a-zA-Z0-9]"), "_")}_cards.pdf"
                val file = File(context.cacheDir, fileName)
                FileOutputStream(file).use { output ->
                    document.writeTo(output)
                }
                document.close()

                onProgress(1f)
                PdfResult.Success(file.absolutePath)
            } catch (e: Exception) {
                PdfResult.Error(e.message ?: "Failed to generate PDF")
            }
        }
    }

    private fun drawFrontPage(canvas: Canvas, cards: List<CustomCard>) {
        val paint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }

        cards.forEachIndexed { index, card ->
            val row = index / CARDS_PER_ROW
            val col = index % CARDS_PER_ROW
            val x = MARGIN_LEFT + col * (CARD_SIZE + CARD_SPACING)
            val y = MARGIN_TOP + row * (CARD_SIZE + CARD_SPACING)

            // Draw card border
            canvas.drawRect(
                x.toFloat(),
                y.toFloat(),
                (x + CARD_SIZE).toFloat(),
                (y + CARD_SIZE).toFloat(),
                paint
            )

            // Generate and draw QR code
            val qrBitmap = generateQrCode(card.deezerId)
            if (qrBitmap != null) {
                val srcRect = Rect(0, 0, qrBitmap.width, qrBitmap.height)
                val destRect = Rect(
                    x + QR_PADDING,
                    y + QR_PADDING,
                    x + QR_PADDING + QR_SIZE,
                    y + QR_PADDING + QR_SIZE
                )
                canvas.drawBitmap(qrBitmap, srcRect, destRect, null)
                qrBitmap.recycle()
            }
        }
    }

    private fun drawBackPage(canvas: Canvas, cards: List<CustomCard>) {
        val borderPaint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }

        val titlePaint = Paint().apply {
            color = Color.BLACK
            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
            textAlign = Paint.Align.CENTER
        }

        val artistPaint = Paint().apply {
            color = Color.DKGRAY
            textSize = 12f
            textAlign = Paint.Align.CENTER
        }

        val yearPaint = Paint().apply {
            color = Color.BLACK
            textSize = 24f
            typeface = Typeface.DEFAULT_BOLD
            textAlign = Paint.Align.CENTER
        }

        // For back pages, mirror the column order for double-sided printing
        cards.forEachIndexed { index, card ->
            val row = index / CARDS_PER_ROW
            val col = index % CARDS_PER_ROW
            // Mirror column for back side
            val mirroredCol = CARDS_PER_ROW - 1 - col
            val x = MARGIN_LEFT + mirroredCol * (CARD_SIZE + CARD_SPACING)
            val y = MARGIN_TOP + row * (CARD_SIZE + CARD_SPACING)

            // Draw card border
            canvas.drawRect(
                x.toFloat(),
                y.toFloat(),
                (x + CARD_SIZE).toFloat(),
                (y + CARD_SIZE).toFloat(),
                borderPaint
            )

            val centerX = x + CARD_SIZE / 2f

            // Draw title (truncate if too long)
            val title = truncateText(card.title, titlePaint, CARD_SIZE - 20)
            canvas.drawText(title, centerX, y + 50f, titlePaint)

            // Draw artist
            val artist = truncateText(card.artist, artistPaint, CARD_SIZE - 20)
            canvas.drawText(artist, centerX, y + 75f, artistPaint)

            // Draw year (large, centered)
            card.year?.let { year ->
                canvas.drawText(year.toString(), centerX, y + CARD_SIZE - 40f, yearPaint)
            }
        }
    }

    private fun generateQrCode(deezerId: Long): Bitmap? {
        return try {
            val url = "https://deezer.com/track/$deezerId"
            val hints = mapOf(
                EncodeHintType.MARGIN to 1,
                EncodeHintType.ERROR_CORRECTION to com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.M
            )
            val bitMatrix = QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE, hints)

            val bitmap = Bitmap.createBitmap(QR_SIZE, QR_SIZE, Bitmap.Config.RGB_565)
            for (x in 0 until QR_SIZE) {
                for (y in 0 until QR_SIZE) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            bitmap
        } catch (e: Exception) {
            null
        }
    }

    private fun truncateText(text: String, paint: Paint, maxWidth: Int): String {
        if (paint.measureText(text) <= maxWidth) return text
        var truncated = text
        while (truncated.isNotEmpty() && paint.measureText("$truncated...") > maxWidth) {
            truncated = truncated.dropLast(1)
        }
        return "$truncated..."
    }

    actual suspend fun sharePdf(filePath: String) {
        withContext(Dispatchers.Main) {
            val file = File(filePath)
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooser = Intent.createChooser(shareIntent, "Share PDF")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        }
    }
}
