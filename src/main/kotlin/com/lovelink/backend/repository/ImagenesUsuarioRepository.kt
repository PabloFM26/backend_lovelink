package com.lovelink.backend.repository

import com.lovelink.backend.entity.ImagenesUsuario
import org.springframework.data.jpa.repository.JpaRepository

interface ImagenesUsuarioRepository : JpaRepository<ImagenesUsuario, Long>{
    fun findByIdUsuario(idUsuario: Long): ImagenesUsuario?
}

