package com.example.projectofinalmoviles.componentes

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectofinalmoviles.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MedicamentosViewModel(private val context: Context) : ViewModel() {
    private val notificationHelper = NotificationHelper(context)

    private val dataStore = DataStoreManager(context)

    private val _medicamentos = mutableStateListOf<Medicamento>()
    val medicamentos: List<Medicamento> = _medicamentos

    private val _alarmaDisparada = MutableStateFlow<Medicamento?>(null)
    val alarmaDisparada: StateFlow<Medicamento?> = _alarmaDisparada.asStateFlow()

    init {

        viewModelScope.launch {
            try {
                dataStore.cargarMedicamentos().collect { medicamentosGuardados ->
                    _medicamentos.clear()
                    _medicamentos.addAll(medicamentosGuardados)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        viewModelScope.launch {
            while (true) {
                verificarAlarmas()
                kotlinx.coroutines.delay(1000L)
            }
        }
    }

    fun agregarMedicamento(medicamento: Medicamento) {
        try {
            val ahora = System.currentTimeMillis()
            val nuevoMedicamento = medicamento.copy(
                proximaDosisTimestamp = ahora + (medicamento.horas * 3600_000L),
                dosisRestantes = medicamento.dosisTotal
            )
            _medicamentos.add(nuevoMedicamento)
            guardarMedicamentos()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun tomarDosis(medicamentoId: String) {
        val index = _medicamentos.indexOfFirst { it.id == medicamentoId }
        if (index != -1) {
            val medicamento = _medicamentos[index]
            if (medicamento.dosisRestantes > 0) {
                val nuevasDosisRestantes = medicamento.dosisRestantes - 1
                if (nuevasDosisRestantes > 0) {
                    val nuevoTimestamp = medicamento.proximaDosisTimestamp + (medicamento.horas * 3600_000L)
                    val medicamentoActualizado = medicamento.copy(
                        dosisRestantes = nuevasDosisRestantes,
                        proximaDosisTimestamp = nuevoTimestamp
                    )
                    _medicamentos[index] = medicamentoActualizado
                } else {
                    _medicamentos.removeAt(index)
                }
                _alarmaDisparada.value = null
                guardarMedicamentos()
            }
        }
    }

    private fun verificarAlarmas() {
        val ahora = System.currentTimeMillis()
        for (medicamento in _medicamentos) {
            if (medicamento.dosisRestantes > 0 &&
                medicamento.proximaDosisTimestamp <= ahora &&
                _alarmaDisparada.value?.id != medicamento.id) {

                _alarmaDisparada.value = medicamento
                notificationHelper.mostrarNotificacion(medicamento)
                break
            }
        }
    }

    private fun guardarMedicamentos() {
        viewModelScope.launch {
            try {
                dataStore.guardarMedicamentos(_medicamentos.toList())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getTiempoRestante(medicamento: Medicamento): Long {
        if (medicamento.dosisRestantes <= 0) return 0
        val ahora = System.currentTimeMillis()
        return maxOf(0, medicamento.proximaDosisTimestamp - ahora)
    }

    fun resetearAlarma() {
        _alarmaDisparada.value = null
    }

    fun eliminarMedicamento(medicamentoId: String) {
        val medicamento = _medicamentos.find { it.id == medicamentoId }
        if (medicamento != null) {
            _medicamentos.remove(medicamento)
            guardarMedicamentos()
        }
    }
    private val prefs = context.getSharedPreferences("config_pref", Context.MODE_PRIVATE)

    fun guardarPreferenciaSonido(sonido: String) {
        prefs.edit().putString("sonido_alarma", sonido).apply()
    }

    fun guardarPreferenciaVolumen(volumen: Float) {
        prefs.edit().putFloat("volumen_alarma", volumen).apply()
    }

    fun obtenerSonidoSeleccionado(): String {
        return prefs.getString("sonido_alarma", "Predeterminado") ?: "Predeterminado"
    }

    fun obtenerVolumen(): Float {
        return prefs.getFloat("volumen_alarma", 0.7f)
    }

    fun probarSonido(sonido: String, volumen: Float) {
        try {
            val sonidoRes = if (sonido == "Predeterminado") {
                R.raw.brightfuture
            } else {
                R.raw.insectfactory
            }

            val mediaPlayer = MediaPlayer.create(context, sonidoRes).apply {
                setVolume(volumen, volumen)
                setOnCompletionListener { release() }
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}