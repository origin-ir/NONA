package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.UserProfile
import com.example.ui.components.BreathingSphereDialog
import com.example.ui.components.CardDetailDialog
import com.example.ui.components.CardRewardCelebration
import com.example.ui.components.CosmicBackground
import com.example.ui.components.ReflectionDialog
import com.example.ui.components.ZenTimerDialog
import com.example.ui.theme.CosmicSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.ZenGold
import com.example.ui.theme.ZenPurplePrimary
import com.example.ui.theme.ZenTeal
import com.example.ui.viewmodel.NonaViewModel

enum class MainTab(val titleFa: String) {
    HOME("روزانه"),
    CARDS("کارت‌ها"),
    SANCTUARY("واحه ذن")
}

@Composable
fun MainScreen(viewModel: NonaViewModel) {
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val cards by viewModel.cards.collectAsStateWithLifecycle()
    val celebrationCard by viewModel.celebrationCard.collectAsStateWithLifecycle()
    val celebrationPoints by viewModel.celebrationPoints.collectAsStateWithLifecycle()
    val activeTask by viewModel.activeActionTask.collectAsStateWithLifecycle()
    val selectedCardDetail by viewModel.selectedCardDetail.collectAsStateWithLifecycle()
    val activeSoundscape by viewModel.activeSoundscape.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableStateOf(MainTab.HOME) }
    var showQuickBreathing by remember { mutableStateOf(false) }

    // If user profile is not ready or not onboarded, show OnboardingScreen
    if (userProfile == null || !userProfile!!.isOnboarded) {
        OnboardingScreen(
            onNameEntered = { name ->
                viewModel.completeOnboarding(name)
            }
        )
        return
    }

    val user = userProfile ?: UserProfile()
    val unlockedCount = cards.count { it.isUnlocked }

    CosmicBackground {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars),
            containerColor = Color.Transparent,
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .testTag("main_navigation_bar"),
                    containerColor = CosmicSurface,
                    tonalElevation = 6.dp
                ) {
                    NavigationBarItem(
                        selected = selectedTab == MainTab.HOME,
                        onClick = { selectedTab = MainTab.HOME },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == MainTab.HOME) Icons.Filled.Spa else Icons.Outlined.Spa,
                                contentDescription = "خانه",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = MainTab.HOME.titleFa,
                                fontWeight = if (selectedTab == MainTab.HOME) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ZenTeal,
                            selectedTextColor = ZenTeal,
                            indicatorColor = Color(0xFF1B2342),
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_home")
                    )

                    NavigationBarItem(
                        selected = selectedTab == MainTab.CARDS,
                        onClick = { selectedTab = MainTab.CARDS },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == MainTab.CARDS) Icons.Filled.AutoAwesome else Icons.Outlined.AutoAwesome,
                                contentDescription = "کارت‌ها",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = "${MainTab.CARDS.titleFa} ($unlockedCount)",
                                fontWeight = if (selectedTab == MainTab.CARDS) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ZenGold,
                            selectedTextColor = ZenGold,
                            indicatorColor = Color(0xFF332520),
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_cards")
                    )

                    NavigationBarItem(
                        selected = selectedTab == MainTab.SANCTUARY,
                        onClick = { selectedTab = MainTab.SANCTUARY },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == MainTab.SANCTUARY) Icons.Filled.SelfImprovement else Icons.Outlined.SelfImprovement,
                                contentDescription = "واحه آرامش",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = MainTab.SANCTUARY.titleFa,
                                fontWeight = if (selectedTab == MainTab.SANCTUARY) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ZenPurplePrimary,
                            selectedTextColor = ZenPurplePrimary,
                            indicatorColor = Color(0xFF281D4C),
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_sanctuary")
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (selectedTab) {
                    MainTab.HOME -> {
                        HomeScreen(
                            user = user,
                            tasks = tasks,
                            unlockedCardsCount = unlockedCount,
                            totalCardsCount = cards.size,
                            onMoodSelected = { mood -> viewModel.selectMood(mood) },
                            onTaskClick = { task -> viewModel.onTaskClicked(task) },
                            onViewCardsClick = { selectedTab = MainTab.CARDS }
                        )
                    }

                    MainTab.CARDS -> {
                        CardsGalleryScreen(
                            cards = cards,
                            onCardClick = { card -> viewModel.openCardDetail(card) }
                        )
                    }

                    MainTab.SANCTUARY -> {
                        SanctuaryScreen(
                            user = user,
                            activeSoundscape = activeSoundscape,
                            onToggleSoundscape = { viewModel.toggleSoundscape(it) },
                            onStartBreathing = { showQuickBreathing = true }
                        )
                    }
                }
            }
        }

        // Active Task Dialogs
        activeTask?.let { task ->
            when (task.actionType) {
                "BREATHE" -> {
                    BreathingSphereDialog(
                        targetCycles = 4,
                        onComplete = { viewModel.completeCurrentActionTask() },
                        onDismiss = { viewModel.closeActionDialog() }
                    )
                }
                "TIMER" -> {
                    ZenTimerDialog(
                        taskTitle = task.titleFa,
                        taskDescription = task.descriptionFa,
                        initialSeconds = 60,
                        onComplete = { viewModel.completeCurrentActionTask() },
                        onDismiss = { viewModel.closeActionDialog() }
                    )
                }
                "REFLECTION" -> {
                    ReflectionDialog(
                        taskTitle = task.titleFa,
                        taskDescription = task.descriptionFa,
                        onComplete = { viewModel.completeCurrentActionTask() },
                        onDismiss = { viewModel.closeActionDialog() }
                    )
                }
            }
        }

        // Quick Breathing Dialog from Sanctuary tab
        if (showQuickBreathing) {
            BreathingSphereDialog(
                targetCycles = 3,
                onComplete = { showQuickBreathing = false },
                onDismiss = { showQuickBreathing = false }
            )
        }

        // Celebration Overlay when task completes and card is unlocked
        celebrationCard?.let { card ->
            CardRewardCelebration(
                card = card,
                pointsWon = celebrationPoints,
                onInspectCard = {
                    viewModel.dismissCelebration()
                    viewModel.openCardDetail(card)
                },
                onDismiss = {
                    viewModel.dismissCelebration()
                }
            )
        }

        // 3D Flip Card Detail Dialog
        selectedCardDetail?.let { card ->
            CardDetailDialog(
                card = card,
                onDismiss = { viewModel.closeCardDetail() }
            )
        }
    }
}
