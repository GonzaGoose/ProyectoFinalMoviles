package com.example.projectofinalmoviles.componentes


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MedicamentosViewModel : ViewModel() {
    private val _medicamentos = mutableStateListOf<Medicamento>()
    val medicamentos: List<Medicamento> = _medicamentos

    fun agregarMedicamento(medicamento: Medicamento) {
        _medicamentos.add(medicamento)
    }
}