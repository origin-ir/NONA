package com.example.ui.components

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.CosmicBackground
import com.example.ui.theme.CosmicSurfaceElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.ZenCyan
import com.example.ui.theme.ZenPurplePrimary
import com.example.ui.theme.ZenTeal
import kotlinx.coroutines.delay

enum class BreathPhase(val titleFa: String, val seconds: Int, val scaleTarget: Float) {
    INHALE("دَم عمیق بکش...", 4, 1.0f),
    HOLD_IN("آرام نگه‌دار...", 4, 1.0f),
    EXHALE("به‌آرامی بازدم کن...", 4, 0.45f),
    HOLD_OUT("سکون و رهایی...", 2, 0.45f)
}

@Composable
fun BreathingSphereDialog(
    targetCycles: Int = 4,
    onComplete: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var currentPhase by remember { mutableStateOf(BreathPhase.INHALE) }
    var phaseSecondsRemaining by remember { mutableIntStateOf(BreathPhase.INHALE.seconds) }
    var completedCycles by remember { mutableIntStateOf(0) }
    var isFinished by remember { mutableStateOf(false) }

    // Haptic feedback function
    val triggerHaptic = remember {
        {
            try {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                if (vibrator != null && vibrator.hasVibrator()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        vibrator.vibrate(50)
                    }
                }
            } catch (e: Exception) {
                // Ignore if not supported
            }
        }
    }

    // Breathing loop timer
    LaunchedEffect(isFinished, currentPhase) {
        if (isFinished) return@LaunchedEffect

        triggerHaptic()
        phaseSecondsRemaining = currentPhase.seconds

        while (phaseSecondsRemaining > 0) {
            delay(1000L)
            phaseSecondsRemaining--
        }

        // Advance phase
        when (currentPhase) {
            BreathPhase.INHALE -> currentPhase = BreathPhase.HOLD_IN
            BreathPhase.HOLD_IN -> currentPhase = BreathPhase.EXHALE
            BreathPhase.EXHALE -> currentPhase = BreathPhase.HOLD_OUT
            BreathPhase.HOLD_OUT -> {
                completedCycles++
                if (completedCycles >= targetCycles) {
                    isFinished = true
                } else {
                    currentPhase = BreathPhase.INHALE
                }
            }
        }
    }

    // Smooth animated scale for breathing sphere
    val sphereScale by animateFloatAsState(
        targetValue = currentPhase.scaleTarget,
        animationSpec = tween(
            durationMillis = currentPhase.seconds * 1000,
            easing = FastOutSlowInEasing
        ),
        label = "sphere_breathing_scale"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CosmicBackground.copy(alpha = 0.96f))
                .padding(24.dp)
        ) {
            // Close button top-right
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(CosmicSurfaceElevated, CircleShape)
                    .testTag("close_breathing_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "بستن",
                    tint = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Heading
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Spa,
                        contentDescription = null,
                        tint = ZenTeal,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "واحه تنفس کیهانی",
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "چرخه ${completedCycles + 1} از $targetCycles",
                        color = ZenTeal,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Breathing Sphere Canvas
                Box(
                    modifier = Modifier
                        .size(280.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val maxRadius = size.width / 2f

                        // Outer radiant ripples
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    ZenPurplePrimary.copy(alpha = 0.25f),
                                    ZenCyan.copy(alpha = 0.05f),
                                    Color.Transparent
                                ),
                                center = center,
                                radius = maxRadius
                            ),
                            radius = maxRadius,
                            center = center
                        )

                        // Inner breathing glowing orb
                        val currentRadius = maxRadius * (0.35f + 0.55f * sphereScale)
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    ZenCyan,
                                    ZenTeal,
                                    ZenPurplePrimary,
                                    Color.Transparent
                                ),
                                center = center,
                                radius = currentRadius
                            ),
                            radius = currentRadius,
                            center = center
                        )
                    }

                    // Countdown text inside sphere
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "$phaseSecondsRemaining",
                            color = Color.White,
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "ثانیه",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )
                    }
                }

                // Phase Instruction & Action
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (isFinished) "تمرین با آرامش کامل پایان یافت ✨" else currentPhase.titleFa,
                        color = TextPrimary,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "ضربان قلبت را حس کن و با انبساط و انقباض نور همراه شو",
                        color = TextMuted,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onComplete,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(52.dp)
                            .testTag("complete_breathing_button"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ZenTeal,
                            contentColor = Color(0xFF042F2E)
                        )
                    ) {
                        Text(
                            text = if (isFinished) "ثبت مأموریت و دریافت کارت جادویی ✨" else "پایان تمرین و دریافت کارت",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
