package com.hitit.app.ui.preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.hitit.app.R

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
    IconVariant("V1 (Original)", R.drawable.ic_launcher_foreground_v1),
    IconVariant("V2 (Shrunk 20%)", R.drawable.ic_launcher_foreground_v2),
    IconVariant("V3 (Notes lowered)", R.drawable.ic_launcher_foreground_v3),
    IconVariant("V4 (Full + notes low)", R.drawable.ic_launcher_foreground_v4),
    IconVariant("V5 (1979 digits)", R.drawable.ic_launcher_foreground_v5),
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
    variants: List<IconVariant> = iconVariants,
    modifier: Modifier = Modifier
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
    widthDp = 400,
    heightDp = 600
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
    widthDp = 500,
    heightDp = 200
)
@Composable
fun IconComparisonPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF5F5F5)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                iconVariants.forEach { variant ->
                    IconPreviewItem(variant = variant)
                }
            }
        }
    }
}
