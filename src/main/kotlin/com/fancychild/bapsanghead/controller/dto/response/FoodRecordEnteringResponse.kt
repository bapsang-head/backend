package com.fancychild.bapsanghead.controller.dto.response

import com.fancychild.bapsanghead.domain.food.dto.FoodRecordMealTypeDto
import com.fancychild.bapsanghead.domain.food.entity.FoodRecord
import com.fancychild.bapsanghead.domain.food.entity.MealType
import com.fancychild.bapsanghead.domain.food.enums.식단입력여부
import java.time.LocalDate
import java.time.YearMonth

data class FoodRecordEnteringResponse(
        val date: LocalDate,
        val 식단입력여부: 식단입력여부
){
    companion object{
        fun toList(
                yearMonth: YearMonth,
                foodRecords: Map<Int, List<FoodRecordMealTypeDto>>
        ): List<FoodRecordEnteringResponse> = (1..yearMonth.month.maxLength()).map { day ->
                val 식단입력여부 = foodRecords[day]?.let {
                    val hasBreakfast = it.any { record -> record.mealType == MealType.BREAKFAST }
                    val hasLunch = it.any { record -> record.mealType == MealType.LUNCH }
                    val hasDinner = it.any { record -> record.mealType == MealType.DINNER }

                    when {
                        hasBreakfast && hasLunch && hasDinner -> 식단입력여부.COMPLETE
                        else -> 식단입력여부.ENTERING
                    }
                } ?: 식단입력여부.NONE

                FoodRecordEnteringResponse(
                        date = LocalDate.of(yearMonth.year, yearMonth.month, day),
                        식단입력여부 = 식단입력여부
                )
            }.sortedBy { it.date }

    }
}

