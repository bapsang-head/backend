package com.fancychild.bapsanghead.controller.dto.response

import com.fancychild.bapsanghead.client.FoodInformationResponse

data class FoodInformationResponse(
        val name: String,
        val unit: String,
        val gram: Double,
        val calories: Double,
        val carbohydrates: Double,
        val protein: Double,
        val fat: Double,
){
    companion object {
        fun from(
                foodInformationResponse: FoodInformationResponse,
        ): com.fancychild.bapsanghead.controller.dto.response.FoodInformationResponse {
            return FoodInformationResponse(
                    name = foodInformationResponse.food,
                    unit = foodInformationResponse.unit,
                    gram = foodInformationResponse.gram,
                    calories = foodInformationResponse.calories,
                    carbohydrates = foodInformationResponse.carbohydrates,
                    protein = foodInformationResponse.protein,
                    fat = foodInformationResponse.fat,
            )
        }
    }
}
