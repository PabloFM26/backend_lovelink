package com.lovelink.backend.controller
import org.springframework.data.jpa.repository.JpaRepository
import com.lovelink.backend.entity.Cuenta
import com.lovelink.backend.repository.CuentaRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cuentas")
class CuentaController(
    private val cuentaRepository: CuentaRepository
) {
    @PostMapping
    fun crearCuenta(@RequestBody cuenta: Cuenta): ResponseEntity<Cuenta> {
        return ResponseEntity.ok(cuentaRepository.save(cuenta))
    }

    @GetMapping
    fun obtenerCuentas(): List<Cuenta> {
        return cuentaRepository.findAll()
    }
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: Cuenta): ResponseEntity<Any> {
        val cuenta = cuentaRepository.findByEmailAndPassword(loginRequest.email, loginRequest.password)
        return if (cuenta != null) {
            ResponseEntity.ok(cuenta)
        } else {
            ResponseEntity.status(401).body(mapOf("error" to "Credenciales incorrectas"))
        }
    }
    // CuentaController.kt
    @GetMapping("/ultima")
    fun obtenerUltimaCuenta(): ResponseEntity<Cuenta?> {
        val cuenta = cuentaRepository.findTopByOrderByIdDesc()
        return ResponseEntity.ok(cuenta)
    }



}
