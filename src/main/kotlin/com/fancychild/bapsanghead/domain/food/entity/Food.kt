package com.fancychild.bapsanghead.domain.food.entity

import com.fancychild.bapsanghead.domain.BaseEntity
import jakarta.persistence.*

@Entity
class Food(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        val name: String,

        val calorie: Double,
        val carbohydrate: Double,
        val protein: Double,
        val fat: Double,
        val unit: String,
        val gram: Double,
):BaseEntity()
