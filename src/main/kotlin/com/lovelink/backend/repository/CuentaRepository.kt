package com.lovelink.backend.repository

import com.lovelink.backend.entity.Cuenta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CuentaRepository : JpaRepository<Cuenta, Long> {
    fun findByEmailAndPassword(email: String, password: String): Cuenta?
}
