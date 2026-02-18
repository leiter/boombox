package com.hitit.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.hitit.app.ui.theme.*
import com.hitit.app.ui.viewmodel.PlaylistImportViewModel
import com.hitit.app.ui.viewmodel.TrackSelection
import hitit.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistImportScreen(
    onBack: () -> Unit,
    onCardSetCreated: (String) -> Unit,
    viewModel: PlaylistImportViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                        text = stringResource(Res.string.import_playlist),
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // URL input
                OutlinedTextField(
                    value = uiState.playlistUrl,
                    onValueChange = { viewModel.updatePlaylistUrl(it) },
                    label = { Text(stringResource(Res.string.playlist_url)) },
                    placeholder = { Text(stringResource(Res.string.playlist_url_placeholder)) },
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

                Spacer(modifier = Modifier.height(12.dp))

                // Fetch button
                Button(
                    onClick = { viewModel.fetchPlaylist() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading && uiState.playlistUrl.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        disabledContainerColor = Primary.copy(alpha = 0.3f)
                    )
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(stringResource(Res.string.fetch_playlist))
                    }
                }

                // Error message
                uiState.error?.let { error ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = error,
                        color = Error,
                        fontSize = 14.sp
                    )
                }

                // Track list
                if (uiState.tracks.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))

                    // Card set name input
                    OutlinedTextField(
                        value = uiState.cardSetName,
                        onValueChange = { viewModel.updateCardSetName(it) },
                        label = { Text(stringResource(Res.string.card_set_name)) },
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Selection controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.selected_count, viewModel.selectedCount, uiState.tracks.size),
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TextButton(onClick = { viewModel.selectAll() }) {
                                Text(stringResource(Res.string.select_all), color = Secondary)
                            }
                            TextButton(onClick = { viewModel.deselectAll() }) {
                                Text(stringResource(Res.string.deselect_all), color = TextSecondary)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Track list
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.tracks) { selection ->
                            TrackSelectionItem(
                                selection = selection,
                                onToggle = { viewModel.toggleTrackSelection(selection.track.id) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Create button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .background(
                                if (viewModel.selectedCount > 0 && uiState.cardSetName.isNotBlank())
                                    Brush.horizontalGradient(listOf(Primary, Secondary))
                                else
                                    Brush.horizontalGradient(listOf(Color.Gray, Color.DarkGray))
                            )
                            .then(
                                if (viewModel.selectedCount > 0 && uiState.cardSetName.isNotBlank() && !uiState.isSaving)
                                    Modifier.clickable {
                                        val cardSetId = viewModel.createCardSet()
                                        if (cardSetId != null) {
                                            onCardSetCreated(cardSetId)
                                        }
                                    }
                                else
                                    Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState.isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = stringResource(Res.string.create_card_set),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TrackSelectionItem(
    selection: TrackSelection,
    onToggle: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp,
                if (selection.isSelected) Primary.copy(alpha = 0.5f) else SurfaceBorder,
                RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onToggle),
        color = if (selection.isSelected) Primary.copy(alpha = 0.1f) else SurfaceLight
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Album cover
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(SurfaceVariantDark)
            ) {
                selection.track.album?.coverMedium?.let { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Track info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = selection.track.title ?: "Unknown",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = selection.track.artist?.name ?: "Unknown",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Checkbox
            Checkbox(
                checked = selection.isSelected,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Primary,
                    uncheckedColor = TextSecondary,
                    checkmarkColor = Color.White
                )
            )
        }
    }
}
