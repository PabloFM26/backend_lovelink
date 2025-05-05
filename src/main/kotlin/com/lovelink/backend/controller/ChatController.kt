package com.lovelink.backend.controller

import com.lovelink.backend.entity.Chat
import com.lovelink.backend.repository.ChatRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chats")
class ChatController(
    private val chatRepository: ChatRepository
) {

    @PostMapping
    fun crearChat(@RequestBody chat: Chat): ResponseEntity<Chat> {
        return try {
            val nuevoChat = chatRepository.save(chat)
            ResponseEntity.ok(nuevoChat)
        } catch (e: Exception) {
            println("⚠️ ERROR al guardar chat: ${e.message}")
            e.printStackTrace()
            ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping
    fun obtenerTodosLosChats(): ResponseEntity<List<Chat>> {
        return ResponseEntity.ok(chatRepository.findAll())
    }

    @GetMapping("/existe")
    fun obtenerChatExistente(
        @RequestParam usuario1: Long,
        @RequestParam usuario2: Long
    ): ResponseEntity<Chat> {
        val chat = chatRepository.findByUsuarios(usuario1, usuario2)
        return if (chat != null) {
            ResponseEntity.ok(chat)
        } else {
            ResponseEntity.noContent().build()
        }
    }

}
