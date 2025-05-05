package com.lovelink.backend.repository

import com.lovelink.backend.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.id_cuenta = :idCuenta")
    fun findByCuentaId(@Param("idCuenta") idCuenta: Long): Usuario
}
