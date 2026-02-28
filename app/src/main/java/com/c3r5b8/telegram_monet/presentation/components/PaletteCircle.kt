package com.c3r5b8.telegram_monet.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c3r5b8.telegram_monet.adapters.PALETTE_TONES
import com.c3r5b8.telegram_monet.adapters.schemeFor
import com.c3r5b8.telegram_monet.common.ColorScheme
import palettes.TonalPalette

private const val HCT_TONE_600 = 40

fun paletteExampleTriple(
    argb: Int,
    colorScheme: ColorScheme = ColorScheme.TONAL_SPOT,
): Triple<Color, Color, Color> {
    val scheme = schemeFor(argb, colorScheme)
    return Triple(
        Color(scheme.primaryPalette.tone(HCT_TONE_600)),
        Color(scheme.secondaryPalette.tone(HCT_TONE_600)),
        Color(scheme.tertiaryPalette.tone(HCT_TONE_600)),
    )
}

fun getTonalPalettes(
    argb: Int,
    colorScheme: ColorScheme = ColorScheme.TONAL_SPOT,
): List<Pair<String, TonalPalette>> {
    val scheme = schemeFor(argb, colorScheme)
    return listOf(
        "a1" to scheme.primaryPalette,
        "a2" to scheme.secondaryPalette,
        "a3" to scheme.tertiaryPalette,
        "n1" to scheme.neutralPalette,
        "n2" to scheme.neutralVariantPalette,
    )
}

@Composable
fun TonalPaletteStrips(
    palettes: List<Pair<String, TonalPalette>>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        palettes.forEach { (label, palette) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.width(24.dp),
                )
                PALETTE_TONES.forEach { tone ->
                    Box(
                        modifier = Modifier
							.weight(1f)
							.height(28.dp)
							.background(Color(palette.tone(tone).toLong() or 0xFF000000L))
                    )
                }
            }
        }
    }
}

@Composable
fun LabeledPaletteCircle(
    topColor: Color,
    bottomLeftColor: Color,
    bottomRightColor: Color,
    label: String = "",
    isSelected: Boolean = false,
    circleSize: Dp = 48.dp,
    onClick: (() -> Unit)? = null,
) {
    Column(
		modifier = Modifier
			.then(
				onClick?.let {
					Modifier.combinedClickable(onClick = it)
				} ?: Modifier
			),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        PaletteCircle(
            topColor = topColor,
            bottomLeftColor = bottomLeftColor,
            bottomRightColor = bottomRightColor,
            circleSize = circleSize,
            isSelected = isSelected,
            onClick = onClick,
        )
        if (label.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = label,
                fontSize = 10.sp,
                maxLines = 1,
            )
        }
    }
}

@Composable
fun SimpleColorCircle(
    argb: Int,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
			.size(size)
			.clip(CircleShape)
			.background(Color(argb))
			.then(if (onClick != null) Modifier.combinedClickable(onClick = onClick) else Modifier),
        contentAlignment = Alignment.Center,
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
					.fillMaxSize()
					.background(Color.Black.copy(alpha = 0.3f)),
            )
            Icon(
                modifier = Modifier.size(size * 0.5f),
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}

@Composable
fun PaletteCircle(
    topColor: Color,
    bottomLeftColor: Color,
    bottomRightColor: Color,
    modifier: Modifier = Modifier,
    circleSize: Dp = 48.dp,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
			.size(circleSize)
			.clip(CircleShape)
			.then(
				if (onClick != null || onLongClick != null)
					Modifier.combinedClickable(onClick = onClick ?: {}, onLongClick = onLongClick)
				else Modifier
			),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(color = topColor, startAngle = 180f, sweepAngle = 180f, useCenter = true)
            drawArc(color = bottomLeftColor, startAngle = 90f, sweepAngle = 90f, useCenter = true)
            drawArc(color = bottomRightColor, startAngle = 0f, sweepAngle = 90f, useCenter = true)
            val r = this.size.minDimension / 2
            val strokeWidth = 2.dp.toPx()
            drawLine(Color.White, center, Offset(center.x + r, center.y), strokeWidth)
            drawLine(Color.White, center, Offset(center.x, center.y + r), strokeWidth)
            drawLine(Color.White, center, Offset(center.x - r, center.y), strokeWidth)
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
            )
            Icon(
                modifier = Modifier.size(circleSize * 0.5f),
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}
