package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

private data class StarParticle(
    val xRatio: Float,
    val yRatio: Float,
    val radius: Float,
    val alpha: Float,
    val speed: Float
)

@Composable
fun CosmicBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "cosmic_pulse")
    val glowPhase by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_phase"
    )

    // Generate fixed random stars once
    val stars = remember {
        val rand = Random(42)
        List(40) {
            StarParticle(
                xRatio = rand.nextFloat(),
                yRatio = rand.nextFloat(),
                radius = rand.nextFloat() * 2f + 1f,
                alpha = rand.nextFloat() * 0.5f + 0.2f,
                speed = rand.nextFloat() * 0.5f + 0.5f
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Base gradient
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF070512),
                        Color(0xFF0F0B24),
                        Color(0xFF161033)
                    )
                )
            )

            // Top-left purple aurora glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF8B5CF6).copy(alpha = 0.18f * glowPhase),
                        Color.Transparent
                    ),
                    center = Offset(width * 0.2f, height * 0.15f),
                    radius = width * 0.7f
                ),
                radius = width * 0.7f,
                center = Offset(width * 0.2f, height * 0.15f)
            )

            // Bottom-right cyan/teal glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2DD4BF).copy(alpha = 0.14f * glowPhase),
                        Color.Transparent
                    ),
                    center = Offset(width * 0.85f, height * 0.8f),
                    radius = width * 0.8f
                ),
                radius = width * 0.8f,
                center = Offset(width * 0.85f, height * 0.8f)
            )

            // Draw twinkling stardust
            stars.forEach { star ->
                val dynamicAlpha = (star.alpha * glowPhase * star.speed).coerceIn(0.1f, 0.9f)
                drawCircle(
                    color = Color.White.copy(alpha = dynamicAlpha),
                    radius = star.radius,
                    center = Offset(star.xRatio * width, star.yRatio * height)
                )
            }
        }

        content()
    }
}
