package com.fancychild.bapsanghead.dto.response

import com.fancychild.bapsanghead.client.FoodInformationResponse

data class FoodInformationWithCountResponse(
        val name: String,
        val unit: String,
        val gram: Double,
        val calories: Double,
        val carbohydrates: Double,
        val protein: Double,
        val fat: Double,
        val count: Int,
){
    companion object {
        fun of(
                foodInformationResponse: FoodInformationResponse,
                count: Int,
        ): FoodInformationWithCountResponse {
            return FoodInformationWithCountResponse(
                    name = foodInformationResponse.food,
                    unit = foodInformationResponse.unit,
                    gram = foodInformationResponse.gram,
                    calories = foodInformationResponse.calories,
                    carbohydrates = foodInformationResponse.carbohydrates,
                    protein = foodInformationResponse.protein,
                    fat = foodInformationResponse.fat,
                    count = count,
            )
        }
    }
}
