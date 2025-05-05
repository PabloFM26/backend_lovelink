package com.lovelink.backend.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "chat")
data class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("idChat")
    val idChat: Long = 0,

    @JsonProperty("matchId")
    @Column(name = "match_id")
    val matchId: Long,

    @JsonProperty("usuario1")
    @Column(name = "usuario_1")
    val usuario1: Long,

    @JsonProperty("usuario2")
    @Column(name = "usuario_2")
    val usuario2: Long,

    @JsonProperty("fechaCreacion")
    @Column(name = "fecha_creacion")
    val fechaCreacion: LocalDateTime = LocalDateTime.now(),

    @JsonProperty("estado")
    val estado: String = "activo"
)
