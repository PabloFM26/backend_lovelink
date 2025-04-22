package com.lovelink.backend.controller

import com.lovelink.backend.entity.Usuario
import com.lovelink.backend.repository.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = ["*"])
class UsuarioController(
    private val usuarioRepository: UsuarioRepository
) {
    @PostMapping
    fun crearUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        return ResponseEntity.ok(usuarioRepository.save(usuario))
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

    @GetMapping("/ultimo")
    fun obtenerUltimoUsuario(): ResponseEntity<Usuario> {
        val usuario = usuarioRepository.findTopByOrderByIdDesc()
        return usuario?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/cuenta/{idCuenta}")
    fun obtenerUsuarioPorCuenta(@PathVariable idCuenta: Long): ResponseEntity<Usuario> {
        val usuario = usuarioRepository.findByCuentaId(idCuenta)
        return ResponseEntity.ok(usuario)
    }
}
