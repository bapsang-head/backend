package com.fancychild.bapsanghead.client

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "ai", url = "http://ec2-15-164-110-7.ap-northeast-2.compute.amazonaws.com:5001")
interface AiClient {

    @PostMapping("/first_GPT_API/")
    fun inputFood(
            @RequestBody request: ResultRequest,
            @RequestHeader("Activate-Key") key: String
    ): ResultResponse

    @PostMapping("/second_GPT_API/")
    fun getInformationOfFood(
            @RequestBody request: FoodInformationRequest,
            @RequestHeader("Activate-Key") key: String
    ): FoodInformationResponse

    @PostMapping("/diet")
    fun getDiet(): String
}
data class ResultRequest(
        @JsonProperty("user_input")
        val userInput: String,
)

data class ResultResponse(
        val data: List<FoodResponse>,
){
    data class FoodResponse(
            val food: String,
            val quantity: Int,
            val unit: String,
    )
}

data class FoodInformationRequest(
        val food: String,
        val unit: String,
)

data class FoodInformationResponse(
        val food: String,
        val unit: String,
        val gram: Double,
        val calories: Double,
        val carbohydrates: Double,
        val protein: Double,
        val fat: Double,
)
