package com.fancychild.bapsanghead.domain.food.repository

import com.fancychild.bapsanghead.domain.food.entity.Food
import org.springframework.data.jpa.repository.JpaRepository

interface FoodRepository : JpaRepository<Food, Long>{

    fun findByNameAndUnit(name: String, unit: String): Food?
}
