package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(user: UserProfile)

    @Query("UPDATE user_profile SET zenPoints = zenPoints + :points WHERE id = 1")
    suspend fun addZenPoints(points: Int)

    @Query("UPDATE user_profile SET selectedMood = :mood WHERE id = 1")
    suspend fun updateMood(mood: String)

    @Query("UPDATE user_profile SET name = :name, isOnboarded = 1 WHERE id = 1")
    suspend fun setOnboardedName(name: String)
}
