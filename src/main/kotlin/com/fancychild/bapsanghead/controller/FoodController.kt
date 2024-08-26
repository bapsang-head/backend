package com.fancychild.bapsanghead.controller

import com.fancychild.bapsanghead.client.AiClient
import com.fancychild.bapsanghead.client.ResultRequest
import com.fancychild.bapsanghead.client.ResultResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "음식 API", description = "음식 관련 API")
@RestController
@RequestMapping("/api/v1/foods")
@ApiResponse(responseCode = "200", description = "OK")
@SecurityRequirement(name = "JWT")
class FoodController(
        private val aiClient: AiClient,
        @Value("\${app.ai.key}")
        private val key: String
) {

    @Operation(summary = "식단 입력 API", description = "사용자가 입력한 식단 문장을 입력합니다.")
    @PostMapping("/input")
    fun inputFood(@RequestBody request: ResultRequest): ResponseEntity<ResultResponse>{
        val response = aiClient.inputFood(request, key)
        return ResponseEntity.ok(response)
    }
}
