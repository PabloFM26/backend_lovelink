package com.lovelink.backend.repository

import com.lovelink.backend.entity.Match
import org.springframework.data.jpa.repository.JpaRepository

interface MatchRepository : JpaRepository<Match, Long> {
    fun findByUsuario1AndUsuario2OrUsuario2AndUsuario1(
        usuario1: Long, usuario2: Long, usuario2Alt: Long, usuario1Alt: Long
    ): Match?
    fun findByUsuario1(usuario1: Long): List<Match>
    fun findByUsuario2(usuario2: Long): List<Match>
}
