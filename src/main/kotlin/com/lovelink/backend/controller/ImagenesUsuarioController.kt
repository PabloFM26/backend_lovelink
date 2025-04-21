package com.lovelink.backend.controller

import com.lovelink.backend.entity.ImagenesUsuario
import com.lovelink.backend.repository.ImagenesUsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/imagenes")
class ImagenesUsuarioController(
    private val imagenesUsuarioRepository: ImagenesUsuarioRepository
) {

    @PostMapping
    fun guardarImagenes(@RequestBody imagenes: ImagenesUsuario): ResponseEntity<ImagenesUsuario> {
        val guardado = imagenesUsuarioRepository.save(imagenes)
        return ResponseEntity.ok(guardado)
    }
}
