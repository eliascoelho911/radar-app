package com.github.eliascoelho911.radar.ui.radar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.radar.R
import com.github.eliascoelho911.radar.ui.theme.Black
import com.github.eliascoelho911.radar.ui.theme.Green700
import com.github.eliascoelho911.radar.ui.theme.GreenA400
import com.github.eliascoelho911.radar.ui.theme.Red700
import java.lang.Math.toRadians

private val ThicknessLines = 3.dp
private const val AmountArcs = 4

@Composable
fun Radar(scannerAngDeg: Float, collisionPoints: List<CollisionPoint> = emptyList()) {
    Box {
        Canvas(Modifier
            .fillMaxSize()
            .background(Black)
            .align(Alignment.Center),
            onDraw = {
                drawArcs()
                collisionPoints.filter { it.angDeg <= scannerAngDeg }.forEach { collisionPoint ->
                    drawCollisionPoint(collisionPoint.angDeg)
                }

                drawScannerLine(scannerAngDeg)
            }
        )
        Text(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(8.dp),
            text = stringResource(id = R.string.angle, scannerAngDeg.toInt()),
            color = Color.White,
            fontWeight = FontWeight.Bold)
    }
}

private fun DrawScope.drawArcs() {
    for (index in 1..AmountArcs) {
        val arcSize = arcSize(index)
        drawArc(Green700,
            startAngle = 0f,
            sweepAngle = -180f,
            useCenter = true,
            style = Stroke(ThicknessLines.toPx()),
            size = arcSize,
            topLeft = Offset(size.width.half() - arcSize.width.half(),
                size.height - arcSize.height.half() - ThicknessLines.toPx().half())
        )
    }
}

private fun DrawScope.arcSize(index: Int): Size {
    val diameter = size.height / AmountArcs * index * 2
    return Size(diameter, diameter)
}

private fun Float.half() = this / 2

private fun DrawScope.drawScannerLine(angDeg: Float) {
    drawLine(angDeg, GreenA400)
}

private fun DrawScope.drawLine(angDeg: Float, color: Color, alpha: Float = 1f) {
    drawLine(color,
        start = Offset(size.width.half(), size.height),
        end = getLineEndPoint(angDeg),
        strokeWidth = ThicknessLines.toPx(),
        alpha = alpha
    )
}

private fun DrawScope.getLineEndPoint(angDeg: Float): Offset = when (angDeg) {
    in 0.0..90.0 -> {
        val y = size.width.half() * sin(angDeg) / sin(90 - angDeg)
        if (y > size.height) {
            val distanceFromCenter = size.height * sin(90 - angDeg) /
                    sin(angDeg)
            Offset(posXFromCenter(-distanceFromCenter), 0f)
        } else {
            Offset(0f, posYFromBottom(y))
        }
    }
    in 91.0..180.0 -> {
        val relativeAngDeg = angDeg - 90
        val x = size.height * sin(relativeAngDeg) / sin(90 - relativeAngDeg)
        if (x > size.width) {
            val invertedAngDeg = 90 - relativeAngDeg
            val y = size.width.half() * sin(invertedAngDeg) /
                    sin(90 - invertedAngDeg)
            Offset(size.width, posYFromBottom(y))
        } else {
            Offset(posXFromCenter(x), 0f)
        }
    }
    else -> throw IllegalArgumentException("Angle max is 180º")
}

private fun DrawScope.posXFromCenter(distanceFromCenter: Float) =
    size.width.half() + distanceFromCenter

private fun DrawScope.posYFromBottom(y: Float) =
    size.height - y

private fun sin(deg: Float) =
    kotlin.math.sin(toRadians(deg.toDouble())).toFloat()

private fun DrawScope.drawCollisionPoint(angDeg: Float, alpha: Float = 1f) {
    drawLine(angDeg, Red700, alpha)
}

data class CollisionPoint(val distanceFromCenter: Float, val angDeg: Float)

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360, showBackground = true)
@Composable
fun RadarPreview() {
    Radar(89f)
}