package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val name: String = "",
    val zenPoints: Int = 0,
    val streakDays: Int = 1,
    val lastActiveDate: String = "",
    val selectedMood: String = "آرام 🌊",
    val isOnboarded: Boolean = false
)
