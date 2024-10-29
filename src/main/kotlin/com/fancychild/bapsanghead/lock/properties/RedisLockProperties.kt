package com.fancychild.bapsanghead.lock.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.redis.lock")
data class RedisLockProperties(
    val host: String,
    val port: Int
)
