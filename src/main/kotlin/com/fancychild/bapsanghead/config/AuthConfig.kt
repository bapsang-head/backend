package com.fancychild.bapsanghead.config

import com.fancychild.bapsanghead.config.userid.LoginUserArgumentResolver
import com.fancychild.bapsanghead.interceptor.LoginInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AuthConfig(
        private val loginInterceptor: LoginInterceptor,
        private val loginUserArgumentResolver: LoginUserArgumentResolver
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/api/v1/dev/**")
                .excludePathPatterns("/api/v1/email/**")
                .excludePathPatterns("/api/v1/auth/**")
                .excludePathPatterns("/api/v1/users/register")
                .excludePathPatterns("/api/v1/universities", "/api/v1/universities/{id}/event", "/api/v1/universities/certification")
                .excludePathPatterns("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs", "/error")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginUserArgumentResolver)
        super.addArgumentResolvers(resolvers)
    }
}
