package com.lovelink.backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "imagenes_usuario")
data class ImagenesUsuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id_imagen: Long = 0,

    @Column(nullable = false)
    val id_usuario: Long,

    val imagen1: String?,
    val imagen2: String?,
    val imagen3: String?,
    val imagen4: String?,
    val imagen5: String?,
    val imagen6: String?
)
