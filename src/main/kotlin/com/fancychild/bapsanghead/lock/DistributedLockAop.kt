package com.fancychild.bapsanghead.lock

import com.fancychild.bapsanghead.lock.util.CustomSpringELParser
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * @DistributedLock 선언 시 수행되는 Aop class
 */
@Aspect
@Component
class DistributedLockAop(
    private val redissonClient: RedissonClient,
    private val aopForTransaction: AopForTransaction
) {
    private val log = LoggerFactory.getLogger(DistributedLockAop::class.java)

    @Around("@annotation(com.fancychild.bapsanghead.lock.DistributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val distributedLock = method.getAnnotation(DistributedLock::class.java)

        val key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(
            signature.parameterNames,
            joinPoint.args, distributedLock.key
        )
        val lock: RLock = redissonClient.getLock(key)

        try {
            log.info("Redisson Lock TryLock {} {}", keyValue("serviceName", method.name), keyValue("key", key))
            val available: Boolean = lock.tryLock(
                distributedLock.waitTime, distributedLock.leaseTime,
                distributedLock.timeUnit
            )

            if (!available) {
                return false
            }
            return aopForTransaction.proceed(joinPoint)
        } catch (e: InterruptedException) {
            throw InterruptedException()
        } finally {
            try {
                lock.unlock() // (4)
            } catch (e: IllegalMonitorStateException) {
                log.info(
                    "Redisson Lock Already UnLock {} {}",
                    keyValue("serviceName", method.name),
                    keyValue("key", key)
                )
            }
        }
    }

    private fun keyValue(key: String, value: String): Any {
        return java.lang.String.join(":", key, value)
    }

    companion object {
        private const val REDISSON_LOCK_PREFIX = "LOCK:"
    }
}
