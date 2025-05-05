package com.lovelink.backend.controller

import com.lovelink.backend.entity.Match
import com.lovelink.backend.repository.MatchRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/matches")
class MatchController(
    private val matchRepository: MatchRepository
) {

    @PostMapping
    fun crearMatch(@RequestBody match: Match): ResponseEntity<Match> {
        val savedMatch = matchRepository.save(match)
        return ResponseEntity.ok(savedMatch)
    }

    @GetMapping("/usuario/{usuarioId}")
    fun obtenerMatchesDeUsuario(@PathVariable usuarioId: Long): ResponseEntity<List<Match>> {
        val matches = matchRepository.findByUsuario1(usuarioId) + matchRepository.findByUsuario2(usuarioId)
        return ResponseEntity.ok(matches)
    }

    @PutMapping("/{matchId}")
    fun actualizarLike(@PathVariable matchId: Long, @RequestBody match: Match): ResponseEntity<Match> {
        val existente = matchRepository.findById(matchId)
        return if (existente.isPresent) {
            val actualizado = existente.get().apply {
                likeUsuario1 = match.likeUsuario1
                likeUsuario2 = match.likeUsuario2
            }
            ResponseEntity.ok(matchRepository.save(actualizado))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/buscar")
    fun buscarMatchEntreUsuarios(
        @RequestParam usuario1: Long,
        @RequestParam usuario2: Long
    ): ResponseEntity<Match> {
        val match = matchRepository.findByUsuario1AndUsuario2OrUsuario2AndUsuario1(
            usuario1, usuario2, usuario1, usuario2
        )

        return if (match != null) {
            ResponseEntity.ok(match)
        } else {
            ResponseEntity.notFound().build()
        }
    }


}
