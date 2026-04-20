package com.example.projectofinalmoviles.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import kotlin.math.max

@Composable
fun CardAlarma(medicamento: Medicamento) {
    val horasEnMillis = medicamento.horas * 60 * 60 * 1000L
    var tiempoRestante by remember { mutableStateOf(horasEnMillis) }

    LaunchedEffect(medicamento.id) {
        while (true) {
            delay(1000L)
            tiempoRestante = max(0, tiempoRestante - 1000L)
        }
    }

    val horas = (tiempoRestante / (60 * 60 * 1000)).toInt()
    val minutos = ((tiempoRestante % (60 * 60 * 1000)) / (60 * 1000)).toInt()
    val segundos = ((tiempoRestante % (60 * 1000)) / 1000).toInt()

    val tiempoFormateado = String.format("%02d:%02d:%02d", horas, minutos, segundos)

    val progreso = if (horasEnMillis > 0) {
        1f - (tiempoRestante.toFloat() / horasEnMillis.toFloat())
    } else {
        0f
    }

    Card(
        modifier = Modifier
            .width(400.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF34344A)
        )
    ) {
        Row() {
            Column(
                modifier = Modifier
                    .width(180.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = medicamento.nombre,
                    fontSize = 28.sp,
                    color = Color.White
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Cada ${medicamento.horas}",
                    fontSize = 40.sp,
                    color = Color.White,
                    modifier = Modifier.offset(x = 22.dp)
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "horas",
                    fontSize = 40.sp,
                    color = Color.White,
                    modifier = Modifier.offset(x = 22.dp)
                )
            }
            Column(
                modifier = Modifier.width(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Siguiente dosis en",
                    fontSize = 22.sp,
                    color = Color.White
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = tiempoFormateado,
                    fontSize = 38.sp,
                    color = Color.White
                )
                Spacer(Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier
                        .width(120.dp)
                        .height(40.dp)
                        .padding(top = 8.dp),
                    color = Color.Yellow,
                    trackColor = Color.Black,
                    strokeCap = StrokeCap.Round,
                    drawStopIndicator = {}
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}