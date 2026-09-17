package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CardRarity
import com.example.data.model.CollectedCard
import com.example.ui.components.CardItemView
import com.example.ui.theme.CosmicSurfaceElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ZenGold
import com.example.ui.theme.ZenPurplePrimary
import com.example.ui.theme.ZenTeal

enum class GalleryFilter(val labelFa: String) {
    ALL("همه کارت‌ها"),
    UNLOCKED("آزاد شده"),
    LOCKED("قفل‌شده")
}

@Composable
fun CardsGalleryScreen(
    cards: List<CollectedCard>,
    onCardClick: (CollectedCard) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf(GalleryFilter.ALL) }
    var selectedRarity by remember { mutableStateOf<CardRarity?>(null) }

    val unlockedCount = cards.count { it.isUnlocked }
    val totalCount = cards.size

    val filteredCards = cards.filter { card ->
        val matchesFilter = when (selectedFilter) {
            GalleryFilter.ALL -> true
            GalleryFilter.UNLOCKED -> card.isUnlocked
            GalleryFilter.LOCKED -> !card.isUnlocked
        }
        val matchesRarity = selectedRarity == null || card.rarity == selectedRarity
        matchesFilter && matchesRarity
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("cards_gallery_screen")
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "کلکسیون کارت‌های نونا ✨",
                        style = MaterialTheme.typography.headlineSmall,
                        color = TextPrimary,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "کارت‌های جادویی کسب‌شده از مأموریت‌های روزانه",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }

                Surface(
                    color = CosmicSurfaceElevated,
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, ZenTeal.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "$unlockedCount / $totalCount",
                        color = ZenTeal,
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Status Filter Row (همه / آزاد شده / قفل شده)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GalleryFilter.values().forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) ZenPurplePrimary else CosmicSurfaceElevated,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) ZenGold else Color(0xFF2C2454)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedFilter = filter }
                            .testTag("filter_${filter.name}")
                    ) {
                        Text(
                            text = filter.labelFa,
                            color = if (isSelected) Color.White else TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.padding(vertical = 8.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Rarity Tags Filter Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (selectedRarity == null) ZenTeal else CosmicSurfaceElevated,
                        modifier = Modifier.clickable { selectedRarity = null }
                    ) {
                        Text(
                            text = "همه رده‌ها",
                            color = if (selectedRarity == null) Color.Black else TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }

                items(CardRarity.values()) { rarity ->
                    val isSelected = selectedRarity == rarity
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) ZenTeal else CosmicSurfaceElevated,
                        border = BorderStroke(1.dp, Color(0xFF2C2454)),
                        modifier = Modifier.clickable {
                            selectedRarity = if (isSelected) null else rarity
                        }
                    ) {
                        Text(
                            text = rarity.labelFa,
                            color = if (isSelected) Color.Black else TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }

        // Cards 2-column Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredCards, key = { it.cardId }) { card ->
                CardItemView(
                    card = card,
                    onClick = { onCardClick(card) }
                )
            }
        }
    }
}
