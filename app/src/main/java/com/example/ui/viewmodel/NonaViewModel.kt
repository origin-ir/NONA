package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.NonaDatabase
import com.example.data.model.CollectedCard
import com.example.data.model.DailyTask
import com.example.data.model.UserProfile
import com.example.data.repository.NonaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NonaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NonaRepository

    val userProfile: StateFlow<UserProfile?>
    val tasks: StateFlow<List<DailyTask>>
    val cards: StateFlow<List<CollectedCard>>

    private val _celebrationCard = MutableStateFlow<CollectedCard?>(null)
    val celebrationCard: StateFlow<CollectedCard?> = _celebrationCard.asStateFlow()

    private val _celebrationPoints = MutableStateFlow(0)
    val celebrationPoints: StateFlow<Int> = _celebrationPoints.asStateFlow()

    private val _activeActionTask = MutableStateFlow<DailyTask?>(null)
    val activeActionTask: StateFlow<DailyTask?> = _activeActionTask.asStateFlow()

    private val _selectedCardDetail = MutableStateFlow<CollectedCard?>(null)
    val selectedCardDetail: StateFlow<CollectedCard?> = _selectedCardDetail.asStateFlow()

    private val _activeSoundscape = MutableStateFlow<String?>("آوای کیهانی 🌌")
    val activeSoundscape: StateFlow<String?> = _activeSoundscape.asStateFlow()

    init {
        val db = NonaDatabase.getDatabase(application)
        repository = NonaRepository(db.userDao(), db.taskDao(), db.cardDao())

        userProfile = repository.userProfile.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

        tasks = repository.allTasks.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        cards = repository.allCards.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        viewModelScope.launch {
            repository.initializeIfEmpty()
        }
    }

    fun completeOnboarding(name: String) {
        viewModelScope.launch {
            repository.setUserName(name)
        }
    }

    fun selectMood(mood: String) {
        viewModelScope.launch {
            repository.updateMood(mood)
        }
    }

    fun onTaskClicked(task: DailyTask) {
        if (task.isCompleted) return

        when (task.actionType) {
            "BREATHE", "TIMER", "REFLECTION" -> {
                _activeActionTask.value = task
            }
            "TAP_CHECK" -> {
                executeTaskCompletion(task)
            }
            else -> {
                executeTaskCompletion(task)
            }
        }
    }

    fun closeActionDialog() {
        _activeActionTask.value = null
    }

    fun completeCurrentActionTask() {
        val task = _activeActionTask.value ?: return
        _activeActionTask.value = null
        executeTaskCompletion(task)
    }

    private fun executeTaskCompletion(task: DailyTask) {
        viewModelScope.launch {
            val unlockedCard = repository.completeTask(task)
            _celebrationPoints.value = task.points
            _celebrationCard.value = unlockedCard
        }
    }

    fun dismissCelebration() {
        val card = _celebrationCard.value
        if (card != null) {
            viewModelScope.launch {
                repository.markCardSeen(card.cardId)
            }
        }
        _celebrationCard.value = null
        _celebrationPoints.value = 0
    }

    fun openCardDetail(card: CollectedCard) {
        _selectedCardDetail.value = card
        if (card.isNew) {
            viewModelScope.launch {
                repository.markCardSeen(card.cardId)
            }
        }
    }

    fun closeCardDetail() {
        _selectedCardDetail.value = null
    }

    fun toggleSoundscape(soundName: String) {
        if (_activeSoundscape.value == soundName) {
            _activeSoundscape.value = null
        } else {
            _activeSoundscape.value = soundName
        }
    }
}
