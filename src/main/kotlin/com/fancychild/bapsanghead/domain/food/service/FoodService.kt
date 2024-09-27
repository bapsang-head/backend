package com.fancychild.bapsanghead.domain.food.service

import com.fancychild.bapsanghead.client.AiClient
import com.fancychild.bapsanghead.client.FoodInformationRequest
import com.fancychild.bapsanghead.domain.food.entity.Food
import com.fancychild.bapsanghead.domain.food.repository.FoodRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
@Service
@Transactional(readOnly = true)
class FoodService(
        private val aiClient: AiClient,
        @Value("\${app.ai.key}")
        private val key: String,
        private val foodRepository: FoodRepository
) {

    fun findByNameAndUnit(name: String, unit: String): Food? = foodRepository.findByNameAndUnit(name, unit)

    fun getByNameAndUnit(name: String, unit: String): Food {
        return foodRepository.findByNameAndUnit(name, unit)?: run {
            val informationOfFood = aiClient.getInformationOfFood(FoodInformationRequest(name, unit), key)

            this.createNewFood(
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
    }

    @Transactional
    fun createNewFood(food: Food): Food = foodRepository.save(food)
}

