package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.CardRarity
import com.example.data.model.CollectedCard
import com.example.ui.theme.CosmicSurface
import com.example.ui.theme.CosmicSurfaceElevated
import com.example.ui.theme.RarityCelestial
import com.example.ui.theme.RarityCommon
import com.example.ui.theme.RarityEpic
import com.example.ui.theme.RarityLegendary
import com.example.ui.theme.RarityRare
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.ZenGold

fun getRarityColor(rarity: CardRarity): Color {
    return when (rarity) {
        CardRarity.COMMON -> RarityCommon
        CardRarity.RARE -> RarityRare
        CardRarity.EPIC -> RarityEpic
        CardRarity.LEGENDARY -> RarityLegendary
        CardRarity.CELESTIAL -> RarityCelestial
    }
}

fun getCardDrawableResId(drawableResName: String): Int {
    return when (drawableResName) {
        "img_card_celestial" -> R.drawable.img_card_celestial
        "img_card_zen_water" -> R.drawable.img_card_zen_water
        "img_nona_icon" -> R.drawable.img_nona_icon
        "img_nona_banner" -> R.drawable.img_nona_banner
        else -> R.drawable.img_card_celestial
    }
}

@Composable
fun CardItemView(
    card: CollectedCard,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rarityColor = getRarityColor(card.rarity)

    val infiniteTransition = rememberInfiniteTransition(label = "card_glow")
    val shimmerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "card_shimmer"
    )

    val borderBrush = if (card.isUnlocked) {
        when (card.rarity) {
            CardRarity.CELESTIAL -> Brush.linearGradient(
                listOf(
                    RarityCelestial,
                    ZenGold,
                    Color(0xFF8B5CF6),
                    RarityCelestial
                )
            )
            CardRarity.LEGENDARY -> Brush.linearGradient(
                listOf(ZenGold, Color(0xFFFDE68A), ZenGold)
            )
            CardRarity.EPIC -> Brush.linearGradient(
                listOf(RarityEpic, Color(0xFFC084FC), RarityEpic)
            )
            CardRarity.RARE -> Brush.linearGradient(
                listOf(RarityRare, Color(0xFF7DD3FC), RarityRare)
            )
            CardRarity.COMMON -> Brush.linearGradient(
                listOf(RarityCommon.copy(alpha = 0.6f), Color(0xFF64748B))
            )
        }
    } else {
        Brush.linearGradient(
            listOf(
                Color(0xFF2E284A).copy(alpha = 0.5f),
                Color(0xFF1E1A33).copy(alpha = 0.5f)
            )
        )
    }

    Card(
        modifier = modifier
            .aspectRatio(0.68f)
            .shadow(
                elevation = if (card.isUnlocked) 8.dp else 2.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = if (card.isUnlocked) rarityColor.copy(alpha = 0.4f) else Color.Transparent
            )
            .border(
                border = BorderStroke(
                    width = if (card.isUnlocked) 1.8.dp else 1.dp,
                    brush = borderBrush
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .testTag("card_item_${card.cardId}"),
        colors = CardDefaults.cardColors(
            containerColor = if (card.isUnlocked) CosmicSurfaceElevated else CosmicSurface.copy(alpha = 0.7f)
        )
    ) {
        if (!card.isUnlocked) {
            // Locked Silhouette Card View
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .background(Color(0xFF221C3D), RoundedCornerShape(27.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "کارت قفل است",
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = card.titleFa,
                        color = Color(0xFF94A3B8),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        color = Color(0xFF19142E),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "با انجام تسک باز می‌شود",
                            color = Color(0xFF64748B),
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            // Unlocked Rich Aesthetic Card View
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Card Header Image with Rarity & Element Tags
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = getCardDrawableResId(card.drawableResName)),
                        contentDescription = card.titleFa,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Top Gradient Shade for tags
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Black.copy(alpha = 0.7f), Color.Transparent)
                                )
                            )
                    )

                    // Bottom Gradient Shade over image
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, CosmicSurfaceElevated)
                                )
                            )
                    )

                    // Rarity pill (Top Start)
                    Surface(
                        color = rarityColor.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(bottomEnd = 12.dp, topStart = 16.dp),
                        modifier = Modifier.align(Alignment.TopStart)
                    ) {
                        Text(
                            text = card.rarity.labelFa,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    // Element Tag (Top End)
                    Surface(
                        color = Color.Black.copy(alpha = 0.75f),
                        shape = RoundedCornerShape(bottomStart = 12.dp, topEnd = 16.dp),
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Text(
                            text = card.elementFa,
                            color = TextPrimary,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    // New Badge if unread
                    if (card.isNew) {
                        Surface(
                            color = ZenGold,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "جدید",
                                    tint = Color.Black,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = "جدید",
                                    color = Color.Black,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Card Footer / Description & Power
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CosmicSurfaceElevated)
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = card.titleFa,
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = card.powerStatFa,
                            color = rarityColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "لمس برای مشاهده",
                            color = Color(0xFF94A3B8),
                            fontSize = 9.sp
                        )
                    }
                }
            }
        }
    }
}
