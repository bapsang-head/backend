package com.fancychild.bapsanghead.domain.food.service

import com.fancychild.bapsanghead.client.AiClient
import com.fancychild.bapsanghead.client.FoodInformationRequest
import com.fancychild.bapsanghead.domain.food.dto.FoodRecordMealTypeDto
import com.fancychild.bapsanghead.domain.food.entity.Food
import com.fancychild.bapsanghead.domain.food.entity.FoodRecord
import com.fancychild.bapsanghead.domain.food.entity.MealType
import com.fancychild.bapsanghead.domain.food.repository.FoodRecordRepository
import com.fancychild.bapsanghead.domain.user.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.YearMonth

@Service
@Transactional(readOnly = true)
class FoodRecordService(
        private val aiClient: AiClient,
        @Value("\${app.ai.key}")
        private val key: String,
        private val foodRecordRepository: FoodRecordRepository,
        private val userService: UserService,
        private val foodService: FoodService,
) {

    private val log = LoggerFactory.getLogger(FoodRecordService::class.java)

    @Transactional
    fun uploadFoodRecord(userId: Long, name: String, unit: String, count: Int, date: LocalDate, mealType: MealType) {
        val user = userService.findById(userId)
        val food = foodService.findByNameAndUnit(name, unit)?: run {
            val informationOfFood = aiClient.getInformationOfFood(FoodInformationRequest(name, unit), key)
            log.info("ai 호출됨 informationOfFood: $informationOfFood")

            foodService.createNewFood(
                    Food(
                            name = name,
                            unit = unit,
                            gram = informationOfFood.gram,
                            calorie = informationOfFood.calories,
                            carbohydrate = informationOfFood.carbohydrates,
                            protein = informationOfFood.protein,
                            fat = informationOfFood.fat,
                    )
            )
        }

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

    @Transactional
    fun clearFoodRecords(userId: Long, date: LocalDate, mealType: MealType) {
        foodRecordRepository.deleteAllByUserIdAndDateAndMealType(userId, date, mealType)
    }
}
