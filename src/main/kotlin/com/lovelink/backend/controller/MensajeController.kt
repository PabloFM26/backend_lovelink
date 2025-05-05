package com.lovelink.backend.controller

import com.lovelink.backend.entity.Mensaje
import com.lovelink.backend.repository.MensajeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/mensajes")
class MensajeController(@Autowired val mensajeRepository: MensajeRepository) {

    @GetMapping("/chat/{chatId}")
    fun obtenerMensajesPorChat(@PathVariable chatId: Long): List<Mensaje> {
        return mensajeRepository.findByChatIdOrderByFechaEnvioAsc(chatId)
    }

    @PostMapping
    fun enviarMensaje(@RequestBody mensaje: Mensaje): Mensaje {
        return mensajeRepository.save(mensaje)
    }
}
