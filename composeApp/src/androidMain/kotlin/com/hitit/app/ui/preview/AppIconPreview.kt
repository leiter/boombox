package com.hitit.app.ui.preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cut.the.crap.jukestar.R


/**
 * Data class for icon variants
 */
data class IconVariant(
    val name: String,
    val resourceId: Int
)

/**
 * List of all icon variants to preview.
 * Add new versions here as: IconVariant("V2", R.drawable.ic_launcher_foreground_v2)
 */
val iconVariants = listOf(
//    IconVariant("V1 (Original)", R.drawable.ic_launcher_foreground_v1),
//    IconVariant("V2 (Shrunk 20%)", R.drawable.ic_launcher_foreground_v2),
//    IconVariant("V3 (Notes lowered)", R.drawable.ic_launcher_foreground_v3),
//    IconVariant("V4 (Full + notes low)", R.drawable.ic_launcher_foreground_v4),
//    IconVariant("V5 (1979 digits)", R.drawable.ic_launcher_foreground_v5),
//    IconVariant("V6 (Tilted left)", R.drawable.ic_launcher_foreground_v6),
//    IconVariant("V7 (Tilted right)", R.drawable.ic_launcher_foreground_v7),
//    IconVariant("V8 (Sound waves)", R.drawable.ic_launcher_foreground_v8),
//    IconVariant("V9 (Isometric top)", R.drawable.ic_launcher_foreground_v9),
//    IconVariant("V10 (Bottom-up)", R.drawable.ic_launcher_foreground_v10),
//    IconVariant("V11 (Large notes)", R.drawable.ic_launcher_foreground_v11),
//    IconVariant("V12 (Side profile)", R.drawable.ic_launcher_foreground_v12),
//    IconVariant("V13 (Badge style)", R.drawable.ic_launcher_foreground_v13),
//    IconVariant("V14 (Dynamic motion)", R.drawable.ic_launcher_foreground_v14),
//    IconVariant("V15 (Speaker zoom)", R.drawable.ic_launcher_foreground_v15),
//    IconVariant("V16 (With vinyl)", R.drawable.ic_launcher_foreground_v16),
//    IconVariant("V17 (Neon 80s)", R.drawable.ic_launcher_foreground_v17),
//    IconVariant("V18 (Minimalist)", R.drawable.ic_launcher_foreground_v18),
//    IconVariant("V19 (Diagonal split)", R.drawable.ic_launcher_foreground_v19),
    IconVariant("V20 (Headphones)", R.drawable.ic_launcher_foreground_v20),
    IconVariant("V21 (80s Comic)", R.drawable.ic_launcher_foreground_v21),
    IconVariant("V22 (Comic 85%)", R.drawable.ic_launcher_foreground_v22),
    IconVariant("V23 (Comic rounded)", R.drawable.ic_launcher_foreground_v23),
    IconVariant("V24 (Wood speakers)", R.drawable.ic_launcher_foreground_v24),
    IconVariant("V25 (Black speakers)", R.drawable.ic_launcher_foreground_v25),
    IconVariant("V26 (White speakers)", R.drawable.ic_launcher_foreground_v26),
    IconVariant("V27 (Single speaker)", R.drawable.ic_launcher_foreground_v27),
    IconVariant("V28 (Red retro)", R.drawable.ic_launcher_foreground_v28),
    IconVariant("V29 (Neon speakers)", R.drawable.ic_launcher_foreground_v29),
    IconVariant("V30 (Neon boombox)", R.drawable.ic_launcher_foreground_v30),
    IconVariant("V31 (Neon pulse)", R.drawable.ic_launcher_foreground_v31),
    IconVariant("V32 (Neon notes)", R.drawable.ic_launcher_foreground_v32),
    IconVariant("V33 (DukeStar crown)", R.drawable.ic_launcher_foreground_v33),
    IconVariant("V34 (Star burst)", R.drawable.ic_launcher_foreground_v34),
    IconVariant("V35 (Star speaker)", R.drawable.ic_launcher_foreground_v35),
    IconVariant("V36 (DJ turntable)", R.drawable.ic_launcher_foreground_v36),
    IconVariant("V37 (Duke D star)", R.drawable.ic_launcher_foreground_v37),
    IconVariant("V38 (Headphones star)", R.drawable.ic_launcher_foreground_v38),
    IconVariant("V39 (Mic star)", R.drawable.ic_launcher_foreground_v39),
    IconVariant("V40 (Shooting star)", R.drawable.ic_launcher_foreground_v40),
)

@Composable
fun IconPreviewItem(
    variant: IconVariant,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon with rounded corners (simulating app icon shape)
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
        ) {
            Image(
                painter = painterResource(id = variant.resourceId),
                contentDescription = variant.name,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = variant.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AppIconGallery(
    modifier: Modifier = Modifier,
    variants: List<IconVariant> = iconVariants,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "HitIt App Icon Variants",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Total variants: ${variants.size}",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(variants) { variant ->
                IconPreviewItem(variant = variant)
            }
        }
    }
}

@Preview(
    name = "App Icon Gallery",
    showBackground = true,
    widthDp = 450,
    heightDp = 900
)
@Composable
fun AppIconGalleryPreview() {
    MaterialTheme {
        Surface {
            AppIconGallery()
        }
    }
}

@Preview(
    name = "Single Icon - Large",
    showBackground = true,
    widthDp = 200,
    heightDp = 200
)
@Composable
fun SingleIconPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF5F5F5)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(144.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .border(2.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(32.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground_v1),
                        contentDescription = "Current Icon",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Icon Comparison - Side by Side",
    showBackground = true,
    widthDp = 600,
    heightDp = 200
)
@Composable
fun IconComparisonPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxSize().background(color = Color(0xFFF5F5F5)),

        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Show first 5 variants for comparison
                iconVariants.take(5).forEach { variant ->
                    IconPreviewItem(variant = variant)
                }
            }
        }
    }
}
