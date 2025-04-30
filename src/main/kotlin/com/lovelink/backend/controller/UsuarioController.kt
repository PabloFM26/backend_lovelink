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
    @PutMapping("/actualizar/{id}")
    fun actualizarUsuario(
        @PathVariable id: Long,
        @RequestBody usuarioActualizado: Map<String, Any?>
    ): ResponseEntity<Usuario> {
        val usuarioExistente = usuarioRepository.findById(id)
        return if (usuarioExistente.isPresent) {
            val usuario = usuarioExistente.get()

            usuario.nombre = usuarioActualizado["nombre"] as? String ?: usuario.nombre
            usuario.apellidos = usuarioActualizado["apellidos"] as? String ?: usuario.apellidos
            usuario.genero = usuarioActualizado["genero"] as? String ?: usuario.genero
            usuario.localidad = usuarioActualizado["localidad"] as? String ?: usuario.localidad
            usuario.orientacionSexual = usuarioActualizado["orientacionSexual"] as? String ?: usuario.orientacionSexual
            usuario.signoZodiaco = usuarioActualizado["signoZodiaco"] as? String ?: usuario.signoZodiaco
            usuario.intencion = usuarioActualizado["intencion"] as? String ?: usuario.intencion
            usuario.altura = (usuarioActualizado["altura"] as? Double)?.toInt() ?: usuario.altura

            usuarioRepository.save(usuario)
            ResponseEntity.ok(usuario)
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

    @GetMapping
    fun obtenerTodosLosUsuarios(): ResponseEntity<List<Usuario>> {
        val usuarios = usuarioRepository.findAll()
        return ResponseEntity.ok(usuarios)
    }

}
