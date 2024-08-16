package com.example.malarm

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.example.malarm.ui.theme.AnalogClockHourHandColoor
import com.example.malarm.ui.theme.AnalogClockInnerBoxColo
import com.example.malarm.ui.theme.AnalogClockMinuteHandColoor
import com.example.malarm.ui.theme.AnalogClockOuterBoxColor
import com.example.malarm.ui.theme.AnalogClockSecondHandColoor
import kotlin.math.min



@Composable
fun AnalogClockComponent(
    hour: Int,
    minute: Int,
    second:Int,
)
{
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(fraction=0.6f)
        .aspectRatio(1f)
        .clip(CircleShape)
        .background(AnalogClockOuterBoxColor)
    ){
        Box(modifier = Modifier
            .fillMaxSize(fraction = 0.78f)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(AnalogClockInnerBoxColo)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val diameter = min(size.width, size.height) * 0.9f
                val radius = diameter / 2


                repeat(4) {
                    val start = center - Offset(0f, radius)
                    val end = start + Offset(0f, radius / 40f)
                    rotate(it / 4f * 360) {
                        drawLine(
                            color = Color.Gray,
                            start = start,
                            end = end,
                            strokeWidth = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }


                val secondRatio = second / 60f
                val minuteRatio = minute / 60f
                val hourRatio = hour / 12f


                rotate(hourRatio * 360f, pivot = center) {
                    drawLine(
                        color = AnalogClockHourHandColoor,
                        start = center - Offset(0f, radius * 0.4f),
                        end = center + Offset(0f, radius * 0.1f),
                        strokeWidth = 3.8.dp.toPx(),
                        cap = StrokeCap.Round

                    )
                }
                rotate(minuteRatio * 360f, pivot = center) {
                    drawLine(
                        color = AnalogClockMinuteHandColoor,
                        start = center - Offset(0f, radius * 0.6f),
                        end = center + Offset(0f, radius * 0.1f),
                        strokeWidth = 3.dp.toPx(),
                        cap = StrokeCap.Round

                    )
                }
                rotate(secondRatio * 360f, pivot = center) {
                    drawLine(
                        color = AnalogClockSecondHandColoor,
                        start = center - Offset(0f, radius * 0.7f),
                        end = center + Offset(0f, radius * 0.1f),
                        strokeWidth = 3.dp.toPx(),
                        cap = StrokeCap.Round

                    )
                }
                drawCircle(
                    color = AnalogClockSecondHandColoor,
                    radius = 5.dp.toPx(),
                    center = center
                )

            }
        }

    }
}


