package com.fancychild.bapsanghead.controller

import com.fancychild.bapsanghead.config.userid.LoginUserId
import com.fancychild.bapsanghead.domain.user.dto.UserDetailsDto
import com.fancychild.bapsanghead.domain.user.enums.ActivityLevel
import com.fancychild.bapsanghead.domain.user.enums.Gender
import com.fancychild.bapsanghead.domain.user.service.UserService
import com.fancychild.bapsanghead.dto.request.UpdateUserDetailsRequest
import com.fancychild.bapsanghead.dto.response.UpdateUserDetailsResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "유저 API", description = "유저 관련 API")
@RestController
@RequestMapping("/api/v1/users")
@ApiResponse(responseCode = "200", description = "OK")
@SecurityRequirement(name = "JWT")
class UserController(
        private val userService: UserService
) {
    @Operation(summary = "소셜 로그인", description = "소셜 로그인을 진행합니다.")
    @PatchMapping("/profile")
    fun login(
            @LoginUserId
            @Parameter(hidden = true)
            userId: Long,

            @RequestBody
            request: UpdateUserDetailsRequest
    ): ResponseEntity<UpdateUserDetailsResponse> {
        val updatedUserDetails = UserDetailsDto(
                height = request.height,
                weight = request.weight,
                age = request.age,
                gender = request.gender?.let { Gender.valueOf(it) },
                activityLevel = request.activityLevel?.let { ActivityLevel.valueOf(it) }
        ).let {
            userService.updateProfile(userId, it)
        }

        val response = UpdateUserDetailsResponse.of(updatedUserDetails.toDto())
        return ResponseEntity.ok(response)
    }
}
