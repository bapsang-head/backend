package com.fancychild.bapsanghead.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private var createdAt: Timestamp? = null

    @LastModifiedDate
    @Column(name = "modified_at")
    private var modifiedAt: Timestamp? = null
}
