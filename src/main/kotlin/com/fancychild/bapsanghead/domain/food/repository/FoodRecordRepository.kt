package com.fancychild.bapsanghead.domain.food.repository

import com.fancychild.bapsanghead.domain.food.dto.FoodRecordMealTypeDto
import com.fancychild.bapsanghead.domain.food.entity.FoodRecord
import com.fancychild.bapsanghead.domain.food.entity.MealType
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface FoodRecordRepository : JpaRepository<FoodRecord, Long> {

    fun findByUserIdAndDateAndMealType(userId: Long, date: LocalDate, mealType: MealType): List<FoodRecord>

    fun findByUserIdAndDateBetween(userId: Long, startDate: LocalDate, endDate: LocalDate): List<FoodRecordMealTypeDto>
}
