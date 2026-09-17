package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class CardRarity(val labelFa: String) {
    COMMON("عادی"),
    RARE("کمیاب"),
    EPIC("حماسی"),
    LEGENDARY("افسانه‌ای"),
    CELESTIAL("کیهانی")
}

@Entity(tableName = "collected_cards")
data class CollectedCard(
    @PrimaryKey val cardId: String,
    val titleFa: String,
    val elementFa: String,
    val rarity: CardRarity,
    val powerStatFa: String,
    val quoteFa: String,
    val drawableResName: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null,
    val isNew: Boolean = false
)
