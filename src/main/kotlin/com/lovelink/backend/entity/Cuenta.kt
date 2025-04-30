package com.lovelink.backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "cuenta")
data class Cuenta(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val telefono: String,
    var email: String,
    var password: String
)
