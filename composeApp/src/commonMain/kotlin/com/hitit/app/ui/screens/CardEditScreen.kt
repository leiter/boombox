package com.hitit.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.hitit.app.model.CustomCard
import com.hitit.app.ui.theme.*
import com.hitit.app.ui.viewmodel.CardSetEditViewModel
import hitit.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardEditScreen(
    cardSetId: String,
    cardId: String,
    onBack: () -> Unit,
    viewModel: CardSetEditViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var title by remember { mutableStateOf("") }
    var artist by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var hasLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(cardSetId) {
        viewModel.loadCardSet(cardSetId)
    }

    // Load card data when available
    val card = uiState.cardSet?.cards?.find { it.id == cardId }
    LaunchedEffect(card) {
        if (card != null && !hasLoaded) {
            title = card.title
            artist = card.artist
            year = card.year?.toString() ?: ""
            hasLoaded = true
        }
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
                        text = stringResource(Res.string.edit_card),
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
            } else if (card == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.card_not_found),
                        color = Error
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Album cover preview
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.CenterHorizontally)
                            .clip(RoundedCornerShape(8.dp))
                            .border(2.dp, SurfaceBorder, RoundedCornerShape(8.dp))
                            .background(SurfaceVariantDark)
                    ) {
                        card.albumCoverUrl?.let { url ->
                            AsyncImage(
                                model = url,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    // Title field
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text(stringResource(Res.string.title)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = SurfaceBorder,
                            focusedLabelColor = Primary,
                            unfocusedLabelColor = TextSecondary,
                            cursorColor = Primary,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    // Artist field
                    OutlinedTextField(
                        value = artist,
                        onValueChange = { artist = it },
                        label = { Text(stringResource(Res.string.artist)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = SurfaceBorder,
                            focusedLabelColor = Primary,
                            unfocusedLabelColor = TextSecondary,
                            cursorColor = Primary,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    // Year field
                    OutlinedTextField(
                        value = year,
                        onValueChange = { newValue ->
                            // Only allow numeric input
                            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                year = newValue.take(4)
                            }
                        },
                        label = { Text(stringResource(Res.string.year)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = SurfaceBorder,
                            focusedLabelColor = Primary,
                            unfocusedLabelColor = TextSecondary,
                            cursorColor = Primary,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    // Album title (read-only)
                    card.albumTitle?.let { albumTitle ->
                        OutlinedTextField(
                            value = albumTitle,
                            onValueChange = {},
                            label = { Text(stringResource(Res.string.album)) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            readOnly = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SurfaceBorder,
                                unfocusedBorderColor = SurfaceBorder,
                                focusedLabelColor = TextSecondary,
                                unfocusedLabelColor = TextSecondary,
                                focusedTextColor = TextSecondary,
                                unfocusedTextColor = TextSecondary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Save button
                    Button(
                        onClick = {
                            val updatedCard = card.copy(
                                title = title.trim(),
                                artist = artist.trim(),
                                year = year.toIntOrNull()
                            )
                            viewModel.updateCard(updatedCard)
                            onBack()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = title.isNotBlank() && artist.isNotBlank() && !uiState.isSaving,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            disabledContainerColor = Primary.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.save),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
