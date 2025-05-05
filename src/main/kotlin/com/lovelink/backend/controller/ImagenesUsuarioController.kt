package com.lovelink.backend.controller

import com.lovelink.backend.entity.ImagenesUsuario
import com.lovelink.backend.repository.ImagenesUsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.nio.file.Paths

@RestController
@RequestMapping("/api/imagenes")
class ImagenesUsuarioController(
    private val imagenesUsuarioRepository: ImagenesUsuarioRepository
) {

    val uploadDir: Path = Paths.get(System.getProperty("user.dir"), "uploads")

    init {
        if (!uploadDir.toFile().exists()) {
            uploadDir.toFile().mkdirs()
        }
    }

    @PostMapping("/upload")
    fun subirImagen(
        @RequestParam("file") archivo: MultipartFile,
        @RequestParam("idUsuario") idUsuario: Long,
        @RequestParam("numero") numero: Int
    ): ResponseEntity<String> {
        return try {
            val originalFileName = archivo.originalFilename ?: "default.jpg"
            val extension = if (originalFileName.contains('.')) originalFileName.substringAfterLast('.') else "jpg"
            val formatosPermitidos = listOf("jpg", "jpeg", "png", "webp")

            if (!formatosPermitidos.contains(extension.lowercase())) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("Formato de imagen no permitido: $extension")
            }

            val fileName = "usuario_${idUsuario}_$numero.$extension"
            val filePath = uploadDir.resolve(fileName)

            archivo.transferTo(filePath.toFile())

            val urlAcceso = "http://10.0.2.2:8081/uploads/$fileName"

            synchronized(this) {
                val existente = imagenesUsuarioRepository.findFirstByIdUsuario(idUsuario)
                val imagenes = existente ?: ImagenesUsuario(idUsuario = idUsuario.toInt())

                when (numero) {
                    1 -> imagenes.imagen1 = urlAcceso
                    2 -> imagenes.imagen2 = urlAcceso
                    3 -> imagenes.imagen3 = urlAcceso
                    4 -> imagenes.imagen4 = urlAcceso
                    5 -> imagenes.imagen5 = urlAcceso
                    6 -> imagenes.imagen6 = urlAcceso
                    else -> return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número de imagen inválido")
                }

                imagenesUsuarioRepository.save(imagenes)
                return ResponseEntity.ok(urlAcceso)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen")
        }
    }

    @PutMapping("/actualizar-slot")
    fun actualizarImagenSlot(
        @RequestParam("file") archivo: MultipartFile,
        @RequestParam("idUsuario") idUsuario: Long,
        @RequestParam("numero") numero: Int
    ): ResponseEntity<String> {
        try {
            // Validar slot permitido
            if (numero !in 1..6) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número de imagen inválido")
            }

            val originalFileName = archivo.originalFilename ?: "default.jpg"
            val extension = if (originalFileName.contains('.')) originalFileName.substringAfterLast('.') else "jpg"
            val formatosPermitidos = listOf("jpg", "jpeg", "png", "webp")

            if (!formatosPermitidos.contains(extension.lowercase())) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("Formato de imagen no permitido: $extension")
            }

            // Eliminar cualquier imagen anterior con este número (sin importar extensión)
            val posiblesExt = listOf("jpg", "jpeg", "png", "webp")
            posiblesExt.forEach { ext ->
                val oldFile = uploadDir.resolve("usuario_${idUsuario}_$numero.$ext").toFile()
                if (oldFile.exists()) oldFile.delete()
            }

            // Guardar la nueva imagen
            val fileName = "usuario_${idUsuario}_$numero.$extension"
            val filePath = uploadDir.resolve(fileName)
            archivo.transferTo(filePath.toFile())

            val urlAcceso = "http://10.0.2.2:8081/uploads/$fileName"

            synchronized(this) {
                val existente = imagenesUsuarioRepository.findFirstByIdUsuario(idUsuario)
                val imagenes = existente ?: ImagenesUsuario(idUsuario = idUsuario.toInt())

                when (numero) {
                    1 -> imagenes.imagen1 = urlAcceso
                    2 -> imagenes.imagen2 = urlAcceso
                    3 -> imagenes.imagen3 = urlAcceso
                    4 -> imagenes.imagen4 = urlAcceso
                    5 -> imagenes.imagen5 = urlAcceso
                    6 -> imagenes.imagen6 = urlAcceso
                }

                imagenesUsuarioRepository.save(imagenes)
            }

            return ResponseEntity.ok(urlAcceso)

        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la imagen")
        }
    }



    @GetMapping("/usuario/{idUsuario}")
    fun getImagenesByUsuarioId(@PathVariable idUsuario: Long): ResponseEntity<ImagenesUsuario?> {
        val imagenes = imagenesUsuarioRepository.findFirstByIdUsuario(idUsuario)
        return if (imagenes != null) {
            ResponseEntity.ok(imagenes)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/usuario/{idUsuario}/{numero}")
    fun borrarImagenPorNumero(
        @PathVariable idUsuario: Long,
        @PathVariable numero: Int
    ): ResponseEntity<String> {
        val posiblesExt = listOf("jpg", "jpeg", "png", "webp")
        val archivo = posiblesExt
            .map { ext -> uploadDir.resolve("usuario_${idUsuario}_$numero.$ext").toFile() }
            .firstOrNull { it.exists() }

        return if (archivo != null && archivo.delete()) {
            ResponseEntity.ok("Imagen eliminada correctamente")
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la imagen")
        }
    }

    private fun borrarArchivoSiExiste(ruta: String?) {
        ruta?.let {
            val nombreArchivo = it.substringAfterLast('/')
            val archivo = uploadDir.resolve(nombreArchivo).toFile()
            if (archivo.exists()) archivo.delete()
        }
    }
}