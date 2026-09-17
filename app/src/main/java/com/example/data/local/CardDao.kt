package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.CollectedCard
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * FROM collected_cards")
    fun getAllCards(): Flow<List<CollectedCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCards(cards: List<CollectedCard>)

    @Query("UPDATE collected_cards SET isUnlocked = 1, unlockedAt = :unlockedAt, isNew = 1 WHERE cardId = :cardId")
    suspend fun unlockCard(cardId: String, unlockedAt: Long)

    @Query("UPDATE collected_cards SET isNew = 0 WHERE cardId = :cardId")
    suspend fun markCardSeen(cardId: String)

    @Query("SELECT * FROM collected_cards WHERE cardId = :cardId LIMIT 1")
    suspend fun getCardById(cardId: String): CollectedCard?

    @Query("SELECT COUNT(*) FROM collected_cards")
    suspend fun getCardCount(): Int
}
