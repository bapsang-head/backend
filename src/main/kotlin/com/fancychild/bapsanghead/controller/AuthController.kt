package com.fancychild.bapsanghead.controller

import com.fancychild.bapsanghead.config.userid.LoginUserId
import com.fancychild.bapsanghead.domain.auth.service.AuthService
import com.fancychild.bapsanghead.domain.user.dto.UserDetailsDto
import com.fancychild.bapsanghead.domain.user.entity.Users
import com.fancychild.bapsanghead.domain.user.enums.ActivityLevel
import com.fancychild.bapsanghead.domain.user.enums.Gender
import com.fancychild.bapsanghead.domain.user.enums.Platform
import com.fancychild.bapsanghead.domain.user.service.UserService
import com.fancychild.bapsanghead.controller.dto.request.LoginRequest
import com.fancychild.bapsanghead.controller.dto.request.RegisterRequest
import com.fancychild.bapsanghead.controller.dto.response.AuthResponse
import com.fancychild.bapsanghead.controller.dto.response.AuthToken
import com.fancychild.bapsanghead.jwt.JwtAuthTokenUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "인증 API", description = "인증(로그인) 관련 API")
@RestController
@RequestMapping("/api/v1/auth")
@ApiResponse(responseCode = "200", description = "OK")
class AuthController(
        private val authService: AuthService,
        private val userService: UserService,
        private val jwtAuthTokenUtil: JwtAuthTokenUtil
) {
    @Operation(summary = "소셜 로그인", description = "소셜 로그인을 진행합니다.")
    @PostMapping("/login/{provider}")
    fun login(
            @RequestBody @Valid
            request: LoginRequest,

            @Parameter(example = "kakao", description = "oAuth 제공자 이름")
            @PathVariable("provider")
            provider: String
    ): ResponseEntity<AuthResponse>{
        val platform: Platform = Platform.fromString(provider)
        val authToken: AuthToken = authService.login(platform, request.tokenType, request.accessToken)

        val userId: Long = jwtAuthTokenUtil.getId(authToken.accessToken)
        val loginUser: Users = userService.findById(userId)

        val response = AuthResponse.of(loginUser, authToken)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    @SecurityRequirement(name = "JWT")
    @PostMapping("/register")
    fun register(
            @LoginUserId
            @Parameter(hidden = true)
            userId: Long,

            @RequestBody @Valid
            request: RegisterRequest
    ):ResponseEntity<AuthResponse>{
        val userDetailsDto = UserDetailsDto(
                height = request.height,
                weight = request.weight,
                age = request.age,
                gender = Gender.valueOf(request.gender),
                activityLevel = ActivityLevel.valueOf(request.activityLevel)
        )

        val authToken = userService.register(userId, userDetailsDto)
        val response = AuthResponse.of(userService.findById(userId), authToken)
        return ResponseEntity.ok(response)
    }
}
