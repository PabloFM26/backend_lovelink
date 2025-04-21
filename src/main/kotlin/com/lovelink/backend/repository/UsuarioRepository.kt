package com.lovelink.backend.repository

import com.lovelink.backend.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long>{
    fun findTopByOrderByIdDesc(): Usuario?



}
