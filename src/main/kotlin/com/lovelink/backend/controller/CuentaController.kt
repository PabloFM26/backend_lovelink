package com.lovelink.backend.controller

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

    @GetMapping("/{id}")
    fun getCuentaById(@PathVariable id: Long): ResponseEntity<Cuenta> {
        val cuenta = cuentaRepository.findById(id)
        return if (cuenta.isPresent) {
            ResponseEntity.ok(cuenta.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/cambiar-correo/{id}")
    fun actualizarCorreo(
        @PathVariable id: Long,
        @RequestBody correoActualizado: Map<String, String>
    ): ResponseEntity<Cuenta> {
        val cuentaExistente = cuentaRepository.findById(id)
        return if (cuentaExistente.isPresent) {
            val cuenta = cuentaExistente.get()
            cuenta.email = correoActualizado["email"] ?: cuenta.email
            cuentaRepository.save(cuenta)
            ResponseEntity.ok(cuenta)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/cambiar-password/{id}")
    fun actualizarContrasena(
        @PathVariable id: Long,
        @RequestBody passwordActualizado: Map<String, String>
    ): ResponseEntity<Cuenta> {
        val cuentaExistente = cuentaRepository.findById(id)
        return if (cuentaExistente.isPresent) {
            val cuenta = cuentaExistente.get()
            cuenta.password = passwordActualizado["password"] ?: cuenta.password
            cuentaRepository.save(cuenta)
            ResponseEntity.ok(cuenta)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    @DeleteMapping("/{id}")
    fun eliminarCuenta(@PathVariable id: Long): ResponseEntity<Void> {
        if (cuentaRepository.existsById(id)) {
            cuentaRepository.deleteById(id)
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/email/{email}")
    fun obtenerCuentaPorEmail(@PathVariable email: String): ResponseEntity<Cuenta> {
        val cuenta = cuentaRepository.findByEmail(email)
        return if (cuenta != null) {
            ResponseEntity.ok(cuenta)
        } else {
            ResponseEntity.notFound().build()
        }
    }



}
