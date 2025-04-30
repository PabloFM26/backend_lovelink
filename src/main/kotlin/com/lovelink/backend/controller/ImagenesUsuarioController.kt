package com.lovelink.backend.controller

import com.lovelink.backend.entity.ImagenesUsuario
import com.lovelink.backend.repository.ImagenesUsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/imagenes")
class ImagenesUsuarioController(
    private val imagenesUsuarioRepository: ImagenesUsuarioRepository
) {

    @PostMapping
    fun guardarImagenes(@RequestBody imagenesUsuario: ImagenesUsuario): ResponseEntity<ImagenesUsuario> {
        println("Intentando guardar imágenes de usuario: $imagenesUsuario")
        return try {
            val guardado = imagenesUsuarioRepository.save(imagenesUsuario)
            ResponseEntity.ok(guardado)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }


    @GetMapping("/usuario/{idUsuario}")
    fun getImagenesByUsuarioId(@PathVariable idUsuario: Long): ResponseEntity<ImagenesUsuario?> {
        val imagenes = imagenesUsuarioRepository.findByIdUsuario(idUsuario)
        return if (imagenes != null) {
            ResponseEntity.ok(imagenes)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun actualizarImagenes(@PathVariable id: Long, @RequestBody imagenesActualizadas: ImagenesUsuario): ResponseEntity<ImagenesUsuario> {
        val imagenesExistente = imagenesUsuarioRepository.findById(id)
        return if (imagenesExistente.isPresent) {
            val imagenes = imagenesExistente.get()
            imagenes.imagen1 = imagenesActualizadas.imagen1
            imagenes.imagen2 = imagenesActualizadas.imagen2
            imagenes.imagen3 = imagenesActualizadas.imagen3
            imagenes.imagen4 = imagenesActualizadas.imagen4
            imagenes.imagen5 = imagenesActualizadas.imagen5
            imagenes.imagen6 = imagenesActualizadas.imagen6
            imagenesUsuarioRepository.save(imagenes)
            ResponseEntity.ok(imagenes)
        } else {
            ResponseEntity.notFound().build()
        }
    }

}
