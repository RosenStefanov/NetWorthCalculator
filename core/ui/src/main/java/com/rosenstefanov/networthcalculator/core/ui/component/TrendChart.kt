package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.ui.theme.SemanticAssets
import com.rosenstefanov.networthcalculator.core.ui.theme.SemanticLiabilities

@Composable
fun TrendChart(
    assetsY: List<Float>,
    liabsY: List<Float>,
    surface: Color,
    modifier: Modifier = Modifier,
) {
    val isDark = surface.luminance() < 0.5f
    val bandTopAlpha = if (isDark) 0.34f else 0.28f

    Canvas(
        modifier
            .fillMaxWidth()
            .height(132.dp),
    ) {
        val w = size.width
        val h = size.height

        fun pts(ys: List<Float>): List<Offset> = ys.mapIndexed { i, y ->
            Offset(x = w * i / (ys.size - 1), y = h * y)
        }

        val aPts = pts(assetsY)
        val lPts = pts(liabsY)

        val band = Path().apply {
            moveTo(aPts.first().x, aPts.first().y)
            aPts.drop(1).forEach { lineTo(it.x, it.y) }
            lineTo(w, h)
            lineTo(0f, h)
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
