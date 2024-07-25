package Model

import java.util.UUID

data class listaPacientes(
    val uuid: UUID,
    val nombres: String,
    val apellidos: String,
    val edad: Int,
    val enfermedad: String,
    val numeroHabitacion: Int,
    val numeroCama: Int,
    val medicamentoAsignado: String,
    val fechaNacimiento: String,
    val horaMedicamento: Double
)
