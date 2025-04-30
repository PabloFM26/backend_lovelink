package com.lovelink.backend.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "matches")
data class Match(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id_match: Long = 0,

    @Column(name = "usuario_1")
    @JsonProperty("usuario_1")
    var usuario1: Int,

    @Column(name = "usuario_2")
    @JsonProperty("usuario_2")
    var usuario2: Int,

    @Column(name = "like_usuario_1")
    @JsonProperty("like_usuario_1")
    var likeUsuario1: Boolean? = null,

    @Column(name = "like_usuario_2")
    @JsonProperty("like_usuario_2")
    var likeUsuario2: Boolean? = null
)

