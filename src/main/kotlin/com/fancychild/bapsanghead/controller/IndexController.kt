package com.fancychild.bapsanghead.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController(

) {
    @GetMapping("/index")
    fun index(): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }
}
