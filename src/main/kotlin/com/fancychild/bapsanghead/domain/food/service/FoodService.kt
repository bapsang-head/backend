package com.fancychild.bapsanghead.domain.food.service

import com.fancychild.bapsanghead.domain.food.entity.Food
import com.fancychild.bapsanghead.domain.food.repository.FoodRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FoodService(
        private val foodRepository: FoodRepository
) {

    fun findByNameAndUnit(name: String, unit: String): Food? = foodRepository.findByNameAndUnit(name, unit)


    @Transactional
    fun createNewFood(food: Food): Food = foodRepository.save(food)
}

