package com.lovelink.backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "usuario")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    val id: Long = 0,

    @Column(name = "id_cuenta")
    val id_cuenta: Long,

    var nombre: String?,
    var apellidos: String?,
    var genero: String?,
    var localidad: String?,
    val edad: Int?,
    var orientacionSexual: String?,
    var signoZodiaco: String?,
    var intencion: String?,
    var altura: Int?
)


