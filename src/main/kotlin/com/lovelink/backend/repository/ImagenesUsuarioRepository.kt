package com.lovelink.backend.repository

import com.lovelink.backend.entity.ImagenesUsuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImagenesUsuarioRepository : JpaRepository<ImagenesUsuario, Long> {

    fun findFirstByIdUsuario(idUsuario: Long): ImagenesUsuario?
}
