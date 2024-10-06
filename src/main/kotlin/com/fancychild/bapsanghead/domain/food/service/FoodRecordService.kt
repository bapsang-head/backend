package com.fancychild.bapsanghead.domain.food.service

import com.fancychild.bapsanghead.domain.food.dto.FoodRecordMealTypeDto
import com.fancychild.bapsanghead.domain.food.entity.FoodRecord
import com.fancychild.bapsanghead.domain.food.entity.MealType
import com.fancychild.bapsanghead.domain.food.repository.FoodRecordRepository
import com.fancychild.bapsanghead.domain.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.YearMonth

@Service
@Transactional(readOnly = true)
class FoodRecordService(
        private val foodRecordRepository: FoodRecordRepository,
        private val userService: UserService,
        private val foodService: FoodService,
) {

    @Transactional
    fun uploadFoodRecord(userId: Long, name: String, unit: String, count: Int, date: LocalDate, mealType: MealType) {
        val user = userService.findById(userId)
        val food = foodService.getByNameAndUnit(name, unit)

        foodRecordRepository.save(
                FoodRecord(
                        user = user,
                        food = food,
                        count = count,
                        date = date,
                        mealType = mealType
                )
        )
    }

    fun getFoodRecordsByYearMonth(userId: Long, yearMonth: YearMonth): Map<Int, List<FoodRecordMealTypeDto>> {
        val startDate = LocalDate.of(yearMonth.year, yearMonth.month, 1)
        val endDate = startDate.plusMonths(1).minusDays(1)

        val foodRecords = foodRecordRepository.findByUserIdAndDateBetween(userId, startDate, endDate)

        return foodRecords.groupBy {
            it.date.dayOfMonth
        }
    }

    fun findFoodRecordsByUserIdAndDateAndMealType(userId: Long, date: LocalDate, mealType: MealType): List<FoodRecord> {
        return foodRecordRepository.findByUserIdAndDateAndMealType(userId, date, mealType)
    }
}
