package com.lovelink.backend.controller

import com.lovelink.backend.entity.Usuario
import com.lovelink.backend.repository.UsuarioRepository
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = ["*"])
class UsuarioController(
    private val usuarioRepository: UsuarioRepository
) {
    @PostMapping
    @Transactional
    fun crearUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val usuarioGuardado = usuarioRepository.save(usuario)
        usuarioRepository.flush()

        val confirmado = usuarioRepository.findById(usuarioGuardado.id)
        if (confirmado.isEmpty) {
            throw RuntimeException("El usuario aún no está disponible.")
        }
        return ResponseEntity.ok(usuarioGuardado)
    }



    @PutMapping("/{id}")
    fun actualizarUsuario(
        @PathVariable id: Long,
        @RequestBody usuarioActualizado: Usuario
    ): ResponseEntity<Usuario> {
        val usuario = usuarioRepository.findById(id)
        return if (usuario.isPresent) {
            val existente = usuario.get()
            val actualizado = existente.copy(
                nombre = usuarioActualizado.nombre,
                apellidos = usuarioActualizado.apellidos,
                genero = usuarioActualizado.genero,
                localidad = usuarioActualizado.localidad,
                edad = usuarioActualizado.edad,
                orientacionSexual = usuarioActualizado.orientacionSexual,
                signoZodiaco = usuarioActualizado.signoZodiaco,
                intencion = usuarioActualizado.intencion,
                altura = usuarioActualizado.altura
            )
            ResponseEntity.ok(usuarioRepository.save(actualizado))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/cuenta/{idCuenta}")
    fun obtenerUsuarioPorCuenta(@PathVariable idCuenta: Long): ResponseEntity<Usuario> {
        val usuario = usuarioRepository.findByCuentaId(idCuenta)
        return ResponseEntity.ok(usuario)
    }

    @GetMapping("/{id}")
    fun getUsuarioById(@PathVariable id: Long): ResponseEntity<Usuario?> {
        val usuario = usuarioRepository.findById(id)
        return if (usuario.isPresent) {
            ResponseEntity.ok(usuario.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
