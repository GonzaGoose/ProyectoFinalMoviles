package com.example.projectofinalmoviles.componentes

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.projectofinalmoviles.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AlarmaDialog(viewModel: MedicamentosViewModel) {
    val alarmaDisparada by viewModel.alarmaDisparada.collectAsStateWithLifecycle()
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    val context = LocalContext.current

    LaunchedEffect(alarmaDisparada) {
        if (alarmaDisparada != null) {
            mediaPlayer?.release()
            val sonidoSeleccionado = viewModel.obtenerSonidoSeleccionado()
            val volumen = viewModel.obtenerVolumen()
            val sonidoRes = if (sonidoSeleccionado == "Predeterminado") {
                R.raw.brightfuture
            } else {
                R.raw.insectfactory
            }
            mediaPlayer = MediaPlayer.create(context, sonidoRes).apply {
                setVolume(volumen, volumen)
                setOnCompletionListener {
                    release()
                }
                start()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
        }
    }

    alarmaDisparada?.let { medicamento ->
        AlertDialog(
            onDismissRequest = { viewModel.resetearAlarma() },
            title = {
                Text(
                    stringResource(R.string.alarma_titulo),
                    fontSize = 24.sp,
                    color = Color.Red
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "${stringResource(R.string.medicamento)} ${medicamento.nombre}",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${stringResource(R.string.dosis_restantes_texto)} ${medicamento.dosisRestantes}",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.tomarDosis(medicamento.id)
                        viewModel.resetearAlarma()
                        mediaPlayer?.stop()
                        mediaPlayer?.release()
                        mediaPlayer = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.tomar_ahora))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.resetearAlarma()
                        mediaPlayer?.stop()
                        mediaPlayer?.release()
                        mediaPlayer = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.posponer))
                }
            }
        )
    }
}