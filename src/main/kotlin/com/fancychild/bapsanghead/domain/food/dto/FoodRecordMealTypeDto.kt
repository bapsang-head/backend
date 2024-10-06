package com.fancychild.bapsanghead.domain.food.dto

import com.fancychild.bapsanghead.domain.food.entity.MealType
import java.time.LocalDate

interface FoodRecordMealTypeDto{
    val date: LocalDate
    val mealType: MealType
}
