package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.UserProfile
import com.example.ui.theme.CosmicSurface
import com.example.ui.theme.CosmicSurfaceElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ZenCyan
import com.example.ui.theme.ZenGold
import com.example.ui.theme.ZenPurplePrimary
import com.example.ui.theme.ZenTeal

private val soundscapes = listOf(
    "باران شبانه 🌧️",
    "آوای جنگل و نسیم 🍃",
    "امواج اقیانوس ژرف 🌊",
    "فرکانس کیهانی ۴۳۲Hz 🌌"
)

private val affirmations = listOf(
    "من در این لحظه در صلح و امنیت کامل با جهان هستم.",
    "هر نفسی که می‌کشم، سرشار از طراوت و آرامش است.",
    "سنگینی گذشته را رها می‌کنم و زیبایی حال را در آغوش می‌گیرم.",
    "درون من چشمه‌ای بی‌پایان از آرامش، خلوص و توانایی جاری است.",
    "به ریتم و روند زندگی اعتماد دارم و در سکون خود قدرتمندم."
)

@Composable
fun SanctuaryScreen(
    user: UserProfile,
    activeSoundscape: String?,
    onToggleSoundscape: (String) -> Unit,
    onStartBreathing: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentAffirmationIndex by remember { mutableStateOf(0) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse_eq")
    val waveScale by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "eq_wave"
    )

    // User level calculation
    val zenPoints = user.zenPoints
    val levelTitle = when {
        zenPoints >= 300 -> "استاد ذن نونا 👑"
        zenPoints >= 180 -> "نگهبان صلح درونی 🪷"
        zenPoints >= 80 -> "جوینده آرامش 🌟"
        else -> "مسافر تازه‌کار ذن 🍃"
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("sanctuary_screen"),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 1. Header
        item {
            Column {
                Text(
                    text = "واحه آرامش نونا 🪷",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "فضای تعاملی تنفس، اصوات شفابخش و حکمت درونی",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }
        }

        // 2. Zen Level Status Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(12.dp, RoundedCornerShape(20.dp), spotColor = ZenPurplePrimary)
                    .border(
                        BorderStroke(1.2.dp, Brush.linearGradient(listOf(ZenPurplePrimary, ZenTeal))),
                        RoundedCornerShape(20.dp)
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CosmicSurfaceElevated)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "مرتبه آرامش درونی شما:",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = levelTitle,
                            color = ZenGold,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "$zenPoints امتیاز آرامش تا کنون",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .background(Color(0xFF261D4E), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SelfImprovement,
                            contentDescription = null,
                            tint = ZenPurplePrimary,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }

        // 3. Quick Breathing Oasis Trigger Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(14.dp, RoundedCornerShape(22.dp), spotColor = ZenTeal)
                    .border(
                        BorderStroke(1.2.dp, ZenTeal.copy(alpha = 0.6f)),
                        RoundedCornerShape(22.dp)
                    ),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1E2A))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "گوی تنفس کیهانی",
                                color = TextPrimary,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = "تنفس هماهنگ ۴-۴-۴ برای کاهش استرس",
                                color = ZenTeal,
                                fontSize = 12.sp
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Spa,
                            contentDescription = null,
                            tint = ZenTeal,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onStartBreathing,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("start_quick_breathing_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ZenTeal,
                            contentColor = Color(0xFF042F2E)
                        )
                    ) {
                        Text(
                            text = "شروع یک دور تنفس آگاهانه 🌬️",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // 4. Ambient Soundscape Synthesizer
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "اصوات آرامش‌بخش طبیعت و کیهان",
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    if (activeSoundscape != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.GraphicEq,
                                contentDescription = null,
                                tint = ZenCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "در حال پخش",
                                color = ZenCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    soundscapes.forEach { sound ->
                        val isPlaying = activeSoundscape == sound
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isPlaying) Color(0xFF1E1742) else CosmicSurfaceElevated,
                            border = BorderStroke(
                                1.dp,
                                if (isPlaying) ZenPurplePrimary else Color(0xFF2B224F)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onToggleSoundscape(sound) }
                                .testTag("sound_${sound.take(6)}")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = if (isPlaying) ZenGold else TextMuted,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = sound,
                                        color = if (isPlaying) Color.White else TextSecondary,
                                        fontWeight = if (isPlaying) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 13.sp
                                    )
                                }

                                if (isPlaying) {
                                    Surface(
                                        color = ZenPurplePrimary.copy(alpha = 0.25f),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "فعال",
                                            color = ZenPurplePrimary,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. Daily Affirmation Oracle Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CosmicSurfaceElevated),
                border = BorderStroke(1.dp, Color(0xFF332959))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "جمله‌ی تأکیدی آرامش",
                        color = ZenGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "« ${affirmations[currentAffirmationIndex]} »",
                        color = TextPrimary,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = {
                            currentAffirmationIndex = (currentAffirmationIndex + 1) % affirmations.size
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF221A46),
                            contentColor = ZenGold
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "دریافت جمله بعدی", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
