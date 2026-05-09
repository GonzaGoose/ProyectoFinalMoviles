package com.example.projectofinalmoviles.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.example.projectofinalmoviles.R
import kotlinx.coroutines.delay

@Composable
fun CardAlarma(
    medicamento: Medicamento,
    viewModel: MedicamentosViewModel
) {
    var tiempoRestante by remember { mutableStateOf(viewModel.getTiempoRestante(medicamento)) }

    LaunchedEffect(medicamento.id, medicamento.proximaDosisTimestamp) {
        while (true) {
            tiempoRestante = viewModel.getTiempoRestante(medicamento)
            delay(1000L)
        }
    }

    val horas = (tiempoRestante / 3600_000).toInt()
    val minutos = ((tiempoRestante % 3600_000) / 60_000).toInt()
    val segundos = ((tiempoRestante % 60_000) / 1000).toInt()

    val tiempoFormateado = String.format("%02d:%02d:%02d", horas, minutos, segundos)

    val horasEnMillis = medicamento.horas * 3600_000L
    val progreso = if (horasEnMillis > 0 && medicamento.dosisRestantes > 0) {
        1f - (tiempoRestante.toFloat() / horasEnMillis.toFloat())
    } else {
        0f
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A3E)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = medicamento.nombre,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE0E0E0)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.cada) + " ${medicamento.horas} " + stringResource(R.string.horas),
                    fontSize = 20.sp,
                    color = Color(0xFFB0B0B0)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = " ${medicamento.dosisRestantes} " + stringResource(R.string.dosis_restantes),
                    fontSize = 16.sp,
                    color = Color(0xFFB0B0B0)
                )

                if (medicamento.dosisRestantes == 0) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.completado),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }
            }


            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (medicamento.dosisRestantes > 0) {
                    Text(
                        text = stringResource(R.string.siguiente_dosis_en),
                        fontSize = 14.sp,
                        color = Color(0xFF9E9E9E),
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = tiempoFormateado,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700),
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )

                    LinearProgressIndicator(
                        progress = { progreso },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .offset(y = 12.dp),
                        color = Color(0xFFFFD700),
                        trackColor = Color(0xFF404052),
                        strokeCap = StrokeCap.Round
                    )

                    Button(
                        onClick = { viewModel.tomarDosis(medicamento.id) },
                        enabled = tiempoRestante <= 0,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (tiempoRestante <= 0) Color(0xFF4CAF50) else Color(0xFF555570),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.tomar_dosis))
                    }
                } else {
                    Text(
                        text = stringResource(R.string.tratamiento_finalizado),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF9E9E9E),
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = { viewModel.eliminarMedicamento(medicamento.id) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFFEF5350)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().offset(y = -12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.eliminar),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}