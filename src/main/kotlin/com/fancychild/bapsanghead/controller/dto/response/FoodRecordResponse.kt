package com.fancychild.bapsanghead.controller.dto.response

import com.fancychild.bapsanghead.domain.food.entity.FoodRecord
import com.fancychild.bapsanghead.domain.food.entity.MealType
import java.time.LocalDate

data class FoodRecordResponse(
        val name: String,
        val calorie: Double,
        val carbohydrate: Double,
        val protein: Double,
        val fat: Double,
        val unit: String,
        val gram: Double,
        val count: Int,
        val date: LocalDate,
        val mealType: MealType
){
    companion object{
        fun from(foodRecord: FoodRecord): FoodRecordResponse {
            return FoodRecordResponse(
                    name = foodRecord.food.name,
                    calorie = foodRecord.food.calorie,
                    carbohydrate = foodRecord.food.carbohydrate,
                    protein = foodRecord.food.protein,
                    fat = foodRecord.food.fat,
                    unit = foodRecord.food.unit,
                    gram = foodRecord.food.gram,
                    count = foodRecord.count,
                    date = foodRecord.date,
                    mealType = foodRecord.mealType
            )
        }
    }
}
