package com.fancychild.bapsanghead.controller.dto.request

import com.fancychild.bapsanghead.domain.food.entity.MealType
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class FoodInformationWithCountRequest(
        val mealType: MealType,

        @Schema(example = "2024-09-27")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        val date: LocalDate,

        val data: List<FoodRequest>,
){
    data class FoodRequest(
            val food: String,
            val unit: String,
            val quantity: Int,
    )
}
