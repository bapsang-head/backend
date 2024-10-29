package com.fancychild.bapsanghead.lock.config

import com.fancychild.bapsanghead.lock.properties.RedisLockProperties
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.redisson.config.Config

@Configuration
class RedissonConfig(
    private val redisLockProperties: RedisLockProperties
) {
    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        val redisAddress = (REDISSON_HOST_PREFIX + redisLockProperties.host) + ":" + redisLockProperties.port
        config.useSingleServer().setAddress(redisAddress)

        return Redisson.create(config)
    }

    companion object {
        private const val REDISSON_HOST_PREFIX = "redis://"
    }
}
