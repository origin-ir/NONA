package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_tasks")
data class DailyTask(
    @PrimaryKey val id: String,
    val titleFa: String,
    val descriptionFa: String,
    val categoryFa: String,
    val points: Int,
    val actionType: String, // "BREATHE", "TIMER", "REFLECTION", "TAP_CHECK"
    val isCompleted: Boolean = false,
    val completedAt: Long? = null,
    val cardRewardId: String
)
