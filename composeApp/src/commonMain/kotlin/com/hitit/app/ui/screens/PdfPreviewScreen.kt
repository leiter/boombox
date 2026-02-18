package com.hitit.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hitit.app.ui.theme.*
import com.hitit.app.ui.viewmodel.PdfExportViewModel
import hitit.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfPreviewScreen(
    cardSetId: String,
    onBack: () -> Unit,
    viewModel: PdfExportViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(cardSetId) {
        viewModel.loadCardSet(cardSetId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(BackgroundLight, BackgroundDark)))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top bar
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.export_pdf),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(Res.string.close),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Primary)
                }
            } else if (uiState.cardSet == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.error ?: stringResource(Res.string.card_set_not_found),
                        color = Error
                    )
                }
            } else {
                val cardSet = uiState.cardSet!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Card set info
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, SurfaceBorder, RoundedCornerShape(12.dp)),
                        color = SurfaceLight
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = cardSet.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = stringResource(Res.string.card_count, cardSet.cardCount),
                                fontSize = 16.sp,
                                color = TextSecondary
                            )
                        }
                    }

                    // PDF info
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, SurfaceBorder, RoundedCornerShape(12.dp)),
                        color = SurfaceLight
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = stringResource(Res.string.pdf_info_title),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )

                            val pagesNeeded = (cardSet.cardCount + 8) / 9
                            InfoRow(
                                label = stringResource(Res.string.pdf_pages),
                                value = stringResource(Res.string.pdf_pages_value, pagesNeeded * 2)
                            )
                            InfoRow(
                                label = stringResource(Res.string.pdf_card_size),
                                value = "66mm x 66mm"
                            )
                            InfoRow(
                                label = stringResource(Res.string.pdf_layout),
                                value = stringResource(Res.string.pdf_layout_value)
                            )

                            Divider(color = SurfaceBorder)

                            Text(
                                text = stringResource(Res.string.pdf_print_instructions),
                                fontSize = 12.sp,
                                color = TextSecondary,
                                textAlign = TextAlign.Start
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Progress indicator
                    if (uiState.isGenerating) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            LinearProgressIndicator(
                                progress = { uiState.progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = Primary,
                                trackColor = SurfaceLight
                            )
                            Text(
                                text = stringResource(Res.string.generating_pdf, (uiState.progress * 100).toInt()),
                                fontSize = 14.sp,
                                color = TextSecondary
                            )
                        }
                    }

                    // Error message
                    uiState.error?.let { error ->
                        Text(
                            text = error,
                            color = Error,
                            fontSize = 14.sp
                        )
                    }

                    // Success state
                    if (uiState.generatedPdfPath != null) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Success.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                            color = Success.copy(alpha = 0.1f)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = stringResource(Res.string.pdf_ready),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Success
                                )
                                Button(
                                    onClick = { viewModel.sharePdf() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Success
                                    )
                                ) {
                                    Icon(
                                        Icons.Default.Share,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(stringResource(Res.string.share_pdf))
                                }
                            }
                        }
                    }

                    // Generate button
                    if (uiState.generatedPdfPath == null) {
                        Button(
                            onClick = { viewModel.generatePdf() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            enabled = !uiState.isGenerating && cardSet.cardCount > 0,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Primary,
                                disabledContainerColor = Primary.copy(alpha = 0.3f)
                            ),
                            shape = RoundedCornerShape(28.dp)
                        ) {
                            Text(
                                text = stringResource(Res.string.generate_pdf),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = TextSecondary
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}
