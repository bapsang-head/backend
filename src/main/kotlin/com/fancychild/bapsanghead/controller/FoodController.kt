package com.fancychild.bapsanghead.controller

import com.fancychild.bapsanghead.client.*
import com.fancychild.bapsanghead.config.userid.LoginUserId
import com.fancychild.bapsanghead.domain.food.entity.FoodRecord
import com.fancychild.bapsanghead.domain.food.entity.MealType
import com.fancychild.bapsanghead.domain.food.service.FoodRecordService
import com.fancychild.bapsanghead.domain.food.service.FoodService
import com.fancychild.bapsanghead.dto.request.FoodInformationWithCountRequest
import com.fancychild.bapsanghead.dto.response.FoodInformationResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Tag(name = "음식 API", description = "음식 관련 API")
@RestController
@RequestMapping("/api/v1/foods")
@ApiResponse(responseCode = "200", description = "OK")
@SecurityRequirement(name = "JWT")
class FoodController(
        private val aiClient: AiClient,
        @Value("\${app.ai.key}")
        private val key: String,
        private val foodService: FoodService,
        private val foodRecordService: FoodRecordService
) {

    @Operation(summary = "식단 입력 API", description = "사용자가 입력한 식단 문장을 입력합니다.")
    @PostMapping("/input")
    fun inputFood(@RequestBody request: ResultRequest): ResultResponse = aiClient.inputFood(request, key)

    @GetMapping("/records/date/{date}/type/{mealType}")
    fun getFoodRecord(
            @Parameter(hidden = true)
            @LoginUserId userId: Long,

            @PathVariable("date")
            @Parameter(example = "2024-09-27")
            @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate,

            @PathVariable("mealType") mealType: MealType
    ): List<FoodRecord> {
        return foodRecordService.getFoodRecords(userId, date, mealType)
    }

    @Operation(summary = "음식 정보 조회 API", description = "사용자가 입력한 식단의 영양정보를 가져옵니다.")
    @GetMapping("/information")
    fun getInformationOfFoodRecord(
            @RequestParam food: String,
            @RequestParam unit: String,
    ): FoodInformationResponse {
        val findFood = foodService.findByNameAndUnit(food, unit) ?: throw IllegalArgumentException("음식 정보가 없습니다.")

        return FoodInformationResponse(
                name = findFood.name,
                unit = findFood.unit,
                calories = findFood.calorie,
                carbohydrates = findFood.carbohydrate,
                protein = findFood.protein,
                fat = findFood.fat,
                gram = findFood.gram
        )
    }

    @Operation(summary = "음식 정보 업로드 API", description = "음식 정보를 업로드합니다.")
    @PostMapping("/information")
    fun uploadFoodRecord(
            @Parameter(hidden = true)
            @LoginUserId userId: Long,
            @RequestBody request: FoodInformationWithCountRequest
    ) {
        request.data.forEach {
            foodRecordService.uploadFoodRecord(
                    userId = userId,
                    name = it.food,
                    unit = it.unit,
                    count = it.count,
                    date = LocalDate.now(),
                    mealType = request.mealType
            )
        }
    }
}
