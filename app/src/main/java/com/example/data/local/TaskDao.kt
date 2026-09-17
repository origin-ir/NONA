package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.DailyTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM daily_tasks")
    fun getAllTasks(): Flow<List<DailyTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<DailyTask>)

    @Update
    suspend fun updateTask(task: DailyTask)

    @Query("UPDATE daily_tasks SET isCompleted = :isCompleted, completedAt = :completedAt WHERE id = :taskId")
    suspend fun setTaskCompleted(taskId: String, isCompleted: Boolean, completedAt: Long?)

    @Query("SELECT COUNT(*) FROM daily_tasks")
    suspend fun getTaskCount(): Int
}
