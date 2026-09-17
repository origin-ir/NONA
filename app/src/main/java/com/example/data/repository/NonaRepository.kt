package com.example.data.repository

import com.example.data.local.CardDao
import com.example.data.local.TaskDao
import com.example.data.local.UserDao
import com.example.data.model.CardRarity
import com.example.data.model.CollectedCard
import com.example.data.model.DailyTask
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NonaRepository(
    private val userDao: UserDao,
    private val taskDao: TaskDao,
    private val cardDao: CardDao
) {
    val userProfile: Flow<UserProfile?> = userDao.getUserProfile()
    val allTasks: Flow<List<DailyTask>> = taskDao.getAllTasks()
    val allCards: Flow<List<CollectedCard>> = cardDao.getAllCards()

    suspend fun initializeIfEmpty() {
        // Seed default user if not exists
        if (taskDao.getTaskCount() == 0) {
            val initialTasks = listOf(
                DailyTask(
                    id = "task_breath",
                    titleFa = "تنفس هماهنگ کیهانی",
                    descriptionFa = "۵ دور تنفس ۴-۴-۴ آرام به همراه گوی درخشان نونا بکش",
                    categoryFa = "نَفَس و ذهن 🌬️",
                    points = 50,
                    actionType = "BREATHE",
                    cardRewardId = "card_cosmic_breath"
                ),
                DailyTask(
                    id = "task_water",
                    titleFa = "جرعه‌ای از زلالی حضور",
                    descriptionFa = "یک لیوان آب یا چای گرم بنوش و ۲ دقیقه بدون هیچ شتابی استراحت کن",
                    categoryFa = "لحظه حال 💧",
                    points = 40,
                    actionType = "TIMER",
                    cardRewardId = "card_zen_spring"
                ),
                DailyTask(
                    id = "task_gratitude",
                    titleFa = "ستاره‌ی شکرگزاری",
                    descriptionFa = "یک اتفاق کوچک یا حس قشنگ امروزت را در نونا ثبت کن",
                    categoryFa = "نور شکرگزاری ✨",
                    points = 50,
                    actionType = "REFLECTION",
                    cardRewardId = "card_morning_bloom"
                ),
                DailyTask(
                    id = "task_silence",
                    titleFa = "غوطه‌وری در سکوت",
                    descriptionFa = "۱ دقیقه چشم‌هایت را ببند و به سکوت یا آوای آرام پیرامونت گوش بسپار",
                    categoryFa = "سکون درون 🕊️",
                    points = 45,
                    actionType = "TIMER",
                    cardRewardId = "card_ocean_silence"
                ),
                DailyTask(
                    id = "task_kindness",
                    titleFa = "امواج نیک‌خواهی",
                    descriptionFa = "یک نیت پرمهر یا پیام محبت‌آمیز به خود یا عزیزی هدیه کن",
                    categoryFa = "مهربانی 💛",
                    points = 40,
                    actionType = "REFLECTION",
                    cardRewardId = "card_golden_dawn"
                ),
                DailyTask(
                    id = "task_unwind",
                    titleFa = "رهایی از انقباض تن",
                    descriptionFa = "عضلات فک و شانه‌ات را شل کن، ۳ کشش ملایم بده و لبخند بزن",
                    categoryFa = "بدن و تنش‌زدایی 🧘",
                    points = 35,
                    actionType = "TAP_CHECK",
                    cardRewardId = "card_floating_leaf"
                ),
                DailyTask(
                    id = "task_cosmic_unity",
                    titleFa = "پیوند با بیکران (روز هفتم)",
                    descriptionFa = "مرور دستاوردهای هفته و ۳ دقیقه تعمق آرام برای تکمیل چرخه‌ی هفتگی ذهن‌آگاهی",
                    categoryFa = "حکمت کیهانی 🌌",
                    points = 60,
                    actionType = "BREATHE",
                    cardRewardId = "card_cosmic_star"
                )
            )
            taskDao.insertTasks(initialTasks)
        }

        if (cardDao.getCardCount() == 0) {
            val initialCards = listOf(
                CollectedCard(
                    cardId = "card_cosmic_breath",
                    titleFa = "نَفَس کیهانی",
                    elementFa = "باد اختری 🌌",
                    rarity = CardRarity.CELESTIAL,
                    powerStatFa = "+۸۰ آرامش عمیق",
                    quoteFa = "در هر دم، آگاهی را در آغوش بگیر و در هر بازدم، سنگینی گذشته را به بیکران بسپار.",
                    drawableResName = "img_card_celestial",
                    isUnlocked = false
                ),
                CollectedCard(
                    cardId = "card_zen_spring",
                    titleFa = "چشمه‌ی زلال حضور",
                    elementFa = "آب حیات 🌊",
                    rarity = CardRarity.LEGENDARY,
                    powerStatFa = "+۷۰ شفافیت و خلوص",
                    quoteFa = "آب با صخره نمی‌جنگد، بلکه با نرمی و صبوری مسیر خود را می‌گشاید. زلال و روان باش.",
                    drawableResName = "img_card_zen_water",
                    isUnlocked = false
                ),
                CollectedCard(
                    cardId = "card_morning_bloom",
                    titleFa = "نیلوفر بامداد",
                    elementFa = "طبیعت کهن 🪷",
                    rarity = CardRarity.RARE,
                    powerStatFa = "+۴۵ طراوت روح",
                    quoteFa = "نیلوفر در تاریکی گل و لای می‌روید تا به سوی نور شکوفا شود؛ زیبایی تو از تاب‌آوری‌ات زاده می‌شود.",
                    drawableResName = "img_nona_icon",
                    isUnlocked = false
                ),
                CollectedCard(
                    cardId = "card_aurora_horizon",
                    titleFa = "افق شفق قطبی",
                    elementFa = "نور کیهانی ✨",
                    rarity = CardRarity.EPIC,
                    powerStatFa = "+۶۵ امید و درخشش",
                    quoteFa = "در ژرف‌ترین تاریکی شب، شگفت‌انگیزترین پرده‌های نور به رقص درمی‌آیند.",
                    drawableResName = "img_nona_banner",
                    isUnlocked = false
                ),
                CollectedCard(
                    cardId = "card_ocean_silence",
                    titleFa = "اقیانوس سکون",
                    elementFa = "اقیانوس ژرف 🌀",
                    rarity = CardRarity.EPIC,
                    powerStatFa = "+۶۰ صلح درون",
                    quoteFa = "سطح دریا شاید متلاطم باشد، اما اعماق آن در سکوتی ابدی و باشکوه غوطه‌ور است.",
                    drawableResName = "img_card_zen_water",
                    isUnlocked = false
                ),
                CollectedCard(
                    cardId = "card_golden_dawn",
                    titleFa = "سپیده‌دم زرین",
                    elementFa = "نور خورشید ☀️",
                    rarity = CardRarity.RARE,
                    powerStatFa = "+۵۰ انرژی مهربانی",
                    quoteFa = "مهربانی کوچک امروز تو، نوری است که جهان کسی را گرم‌تر می‌کند.",
                    drawableResName = "img_nona_banner",
                    isUnlocked = false
                ),
                CollectedCard(
                    cardId = "card_floating_leaf",
                    titleFa = "برگ رهای ذن",
                    elementFa = "نسیم رهایی 🍃",
                    rarity = CardRarity.COMMON,
                    powerStatFa = "+۳۰ سبکی خیال",
                    quoteFa = "چیزهایی را که در کنترلت نیست، مثل برگی در باد به جریان هستی واگذار کن.",
                    drawableResName = "img_nona_icon",
                    isUnlocked = false
                ),
                CollectedCard(
                    cardId = "card_cosmic_star",
                    titleFa = "ستاره‌ی راهنما",
                    elementFa = "نور ابدی ⭐",
                    rarity = CardRarity.LEGENDARY,
                    powerStatFa = "+۹۵ بصیرت روحی",
                    quoteFa = "تو قطره‌ای در اقیانوس نیستی؛ تو تمام اقیانوس در یک قطره‌ای.",
                    drawableResName = "img_card_celestial",
                    isUnlocked = false
                )
            )
            cardDao.insertCards(initialCards)
        }
    }

    suspend fun setUserName(name: String) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val profile = UserProfile(
            id = 1,
            name = name.trim(),
            zenPoints = 20, // Initial welcome gift points
            streakDays = 1,
            lastActiveDate = today,
            selectedMood = "آرام 🌊",
            isOnboarded = true
        )
        userDao.insertOrUpdate(profile)
    }

    suspend fun completeTask(task: DailyTask): CollectedCard? {
        val now = System.currentTimeMillis()
        taskDao.setTaskCompleted(task.id, true, now)
        userDao.addZenPoints(task.points)

        // Unlock associated card
        cardDao.unlockCard(task.cardRewardId, now)
        return cardDao.getCardById(task.cardRewardId)
    }

    suspend fun updateMood(mood: String) {
        userDao.updateMood(mood)
    }

    suspend fun markCardSeen(cardId: String) {
        cardDao.markCardSeen(cardId)
    }
}
