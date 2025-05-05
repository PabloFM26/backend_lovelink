package com.lovelink.backend.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "mensaje")
data class Mensaje(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idMensaje: Long = 0,

    @Column(name = "chat_id", nullable = false)
    val chatId: Long,

    @Column(nullable = false)
    val emisor: Long,

    @Column(nullable = false, columnDefinition = "TEXT")
    val contenido: String,

    @Column(name = "fecha_envio", nullable = false)
    val fechaEnvio: Timestamp = Timestamp(System.currentTimeMillis())
)
