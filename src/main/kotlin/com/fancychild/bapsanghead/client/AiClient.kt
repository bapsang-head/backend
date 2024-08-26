package com.fancychild.bapsanghead.client

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Value
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
            val quantity: String,
            val unit: String,
    )
}

data class DietRequest(
        val data: List<FoodRequest>,
){
    data class FoodRequest(
            val index: String,
            val word: Int,
            val tag: Int,
    )
}

data class DietResponse(
        val diet: String,
)
