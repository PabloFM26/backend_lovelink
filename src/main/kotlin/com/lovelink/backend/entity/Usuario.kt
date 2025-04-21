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

    val nombre: String?,
    val apellidos: String?,
    val genero: String?,
    val localidad: String?,
    val edad: Int?,
    val orientacionSexual: String?,
    val signoZodiaco: String?,
    val intencion: String?,
    val altura: Int?
)


