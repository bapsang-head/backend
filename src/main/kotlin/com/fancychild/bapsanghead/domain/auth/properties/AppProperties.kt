package com.fancychild.bapsanghead.domain.auth.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding

@ConfigurationProperties(prefix = "app")
@ConfigurationPropertiesBinding
@JvmRecord
data class AppProperties(
        val kakao: KaKaoProperties
) {
    @ConfigurationPropertiesBinding
    data class KaKaoProperties(
            val userInfoUri: String,

            val clientId: String,

            val clientSecret: String
    )
}
