package com.fancychild.bapsanghead.dto.request

import com.fancychild.bapsanghead.domain.food.entity.MealType

data class FoodInformationWithCountRequest(
        val mealType: MealType,
        val data: List<FoodRequest>,
){
    data class FoodRequest(
            val food: String,
            val unit: String,
            val count: Int,
    )
}
