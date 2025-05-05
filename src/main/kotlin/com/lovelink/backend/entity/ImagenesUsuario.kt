package com.lovelink.backend.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "imagenes_usuario")
data class ImagenesUsuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id_imagen: Long = 0,

    @Column(name = "id_usuario", unique = true)
    @JsonProperty("id_usuario")
    var idUsuario: Int,


    var imagen1: String? = null,
    var imagen2: String? = null,
    var imagen3: String? = null,
    var imagen4: String? = null,
    var imagen5: String? = null,
    var imagen6: String? = null
)
