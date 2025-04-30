package com.lovelink.backend.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "imagenes_usuario")
data class ImagenesUsuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id_imagen: Long = 0,

    @Column(name = "id_usuario")
    @JsonProperty("id_usuario")
    var idUsuario: Int,


    var imagen1: String?,
    var imagen2: String?,
    var imagen3: String?,
    var imagen4: String?,
    var imagen5: String?,
    var imagen6: String?
)
