package com.lovelink.backend.repository

import com.lovelink.backend.entity.Mensaje
import org.springframework.data.jpa.repository.JpaRepository

interface MensajeRepository : JpaRepository<Mensaje, Long> {
    fun findByChatIdOrderByFechaEnvioAsc(chatId: Long): List<Mensaje>
}
