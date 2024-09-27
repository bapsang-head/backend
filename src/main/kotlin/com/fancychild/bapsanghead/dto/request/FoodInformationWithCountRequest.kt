package com.fancychild.bapsanghead.dto.request

data class FoodInformationWithCountRequest(
        val data: List<FoodRequest>,
){
    data class FoodRequest(
            val food: String,
            val unit: String,
            val count: Int,
    )
}
