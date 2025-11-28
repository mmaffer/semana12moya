package edu.pe.semana12moya.model

data class Product(
    val id: String = "",
    val nombre: String = "",
    val codigo: String = "",
    val descripcion: String = "",
    val precio: Double = 0.0,
    val cantidad: Int = 0,
    val estado: String = "",
    val userId: String = ""   // Para filtrar según el usuario
)


