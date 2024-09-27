package com.fancychild.bapsanghead.domain.food.entity

import com.fancychild.bapsanghead.domain.BaseEntity
import com.fancychild.bapsanghead.domain.user.entity.Users
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class FoodRecord(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        val user: Users,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "food_id")
        val food: Food,

        val count: Int,

        val date: LocalDate,

        val mealType: MealType
): BaseEntity()

enum class MealType {
    BREAKFAST, LUNCH, DINNER
}
