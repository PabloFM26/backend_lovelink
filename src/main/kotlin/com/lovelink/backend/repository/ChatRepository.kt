package com.lovelink.backend.repository

import com.lovelink.backend.entity.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ChatRepository : JpaRepository<Chat, Long> {
    fun findByMatchId(matchId: Long): Chat?
    @Query("SELECT c FROM Chat c WHERE (c.usuario1 = :usuario1 AND c.usuario2 = :usuario2) OR (c.usuario1 = :usuario2 AND c.usuario2 = :usuario1)")
    fun findByUsuarios(@Param("usuario1") usuario1: Long, @Param("usuario2") usuario2: Long): Chat?

}
