package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.SemanticAssets
import com.rosenstefanov.networthcalculator.core.ui.theme.SemanticLiabilities
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToInt

private val ChartHeight = 132.dp
private val YAxisWidth = 30.dp

private const val TopPad = 0.06f
private const val BottomPad = 0.06f
private const val Usable = 1f - TopPad - BottomPad

@Composable
fun TrendChart(
    assetsValues: List<Float>,
    liabilitiesValues: List<Float>,
    surface: Color,
    modifier: Modifier = Modifier,
    formatAxisLabel: (Float) -> String = ::defaultTrendAxisLabel,
) {
    val isDark = surface.luminance() < 0.5f
    val bandTopAlpha = if (isDark) 0.34f else 0.28f
    val gridColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color(0xFFE7E9F2)

    val dataMax = max(
        assetsValues.maxOrNull() ?: 0f,
        liabilitiesValues.maxOrNull() ?: 0f,
    )
    val (axisMax, tickValues) = niceAxis(dataMax)
    fun fractionOf(value: Float): Float = TopPad + (axisMax - value) / axisMax * Usable

    Row(
        modifier = modifier.height(ChartHeight),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(
            Modifier
                .width(YAxisWidth)
                .fillMaxHeight(),
        ) {
            tickValues.forEach { value ->
                Text(
                    text = formatAxisLabel(value),
                    fontFamily = JakartaSans,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Medium,
                    color = NetWorthTheme.extendedColors.inkSub,
                    maxLines = 1,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(y = ChartHeight * fractionOf(value) - 6.dp),
                )
            }
        }

        Canvas(
            Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            val w = size.width
            val h = size.height

            tickValues.forEach { value ->
                val y = h * fractionOf(value)
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y),
                    end = Offset(w, y),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 4f)),
                )
            }

            fun pts(values: List<Float>): List<Offset> = values.mapIndexed { i, v ->
                Offset(x = w * i / (values.size - 1), y = h * fractionOf(v))
            }

            val aPts = pts(assetsValues)
            val lPts = pts(liabilitiesValues)

            val band = Path().apply {
                moveTo(aPts.first().x, aPts.first().y)
                aPts.drop(1).forEach { lineTo(it.x, it.y) }
                lPts.reversed().forEach { lineTo(it.x, it.y) }
                close()
            }
            drawPath(
                path = band,
                brush = Brush.verticalGradient(
                    0f to SemanticAssets.copy(alpha = bandTopAlpha),
                    1f to Color(0xFF8A2EE8).copy(alpha = 0.04f),
                ),
            )

            fun line(pts: List<Offset>): Path = Path().apply {
                moveTo(pts.first().x, pts.first().y)
                pts.drop(1).forEach { lineTo(it.x, it.y) }
            }

            val stroke = Stroke(width = 2.4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
            drawPath(line(aPts), SemanticAssets, style = stroke)
            drawPath(line(lPts), SemanticLiabilities, style = stroke)

            val dotR = 3.4.dp.toPx()
            val haloR = dotR + 2.dp.toPx()
            listOf(aPts.last() to SemanticAssets, lPts.last() to SemanticLiabilities).forEach { (p, c) ->
                drawCircle(surface, radius = haloR, center = p)
                drawCircle(c, radius = dotR, center = p)
            }
        }
    }
}

private const val TargetIntervals = 3

internal fun niceAxis(dataMax: Float): Pair<Float, List<Float>> {
    if (dataMax <= 0f) return 1f to listOf(0f, 1f)

    val rawStep = dataMax / TargetIntervals
    val magnitude = 10.0.pow(floor(log10(rawStep.toDouble()))).toFloat()
    val normalized = rawStep / magnitude
    val niceNormalized = when {
        normalized <= 1f -> 1f
        normalized <= 1.5f -> 1.5f
        normalized <= 2f -> 2f
        normalized <= 2.5f -> 2.5f
        normalized <= 5f -> 5f
        else -> 10f
    }
    val step = niceNormalized * magnitude
    val niceMax = ceil(dataMax / step) * step
    val count = (niceMax / step).roundToInt()
    return niceMax to (0..count).map { it * step }
}

internal fun defaultTrendAxisLabel(value: Float): String = "$" + compactNumber(value)

fun compactNumber(value: Float): String {
    val rounded = value.roundToInt()
    return when {
        rounded >= 1_000_000 -> {
            val millions = rounded / 100_000 / 10f // one decimal
            (if (millions % 1f == 0f) millions.toInt().toString() else millions.toString()) + "M"
        }
        rounded >= 1_000 -> "${rounded / 1_000}k"
        else -> rounded.toString()
    }
}
