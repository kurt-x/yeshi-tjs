package com.yeshi.tjs.core

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import java.io.Serializable
import java.time.Instant
import java.util.*

/** # Token */
class AuthenticationToken(
    private val userId: UUID, private val username: String, authorities: Collection<GrantedAuthority>
) :
    AbstractAuthenticationToken(authorities)
{
    init
    {
        super.setAuthenticated(true)
    }

    /** ### 用户 UUID */
    override fun getPrincipal(): UUID = userId

    /** ### 用户名 */
    override fun getName() = username

    override fun getCredentials() = throw UnsupportedOperationException()

    override fun setAuthenticated(authenticated: Boolean) = throw UnsupportedOperationException()
}

/** # 持久化对象基类 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BasePO : Serializable
{
    /** 备注 */
    @Column(comment = "备注")
    var remark: String? = null

    /** 数据创建者 ID */
    @CreatedBy
    @Column(updatable = false, comment = "数据创建者 ID")
    var creatorId: Long? = null

    /** 数据创建时间 */
    @CreatedDate
    @Column(nullable = false, updatable = false, comment = "数据创建时间")
    var createdTime: Instant? = null

    /** 数据最后修改者 ID */
    @LastModifiedBy
    @Column(comment = "数据最后修改者 ID")
    var lastModifierId: Long? = null

    /** 数据最后修改时间 */
    @LastModifiedDate
    @Column(nullable = false, comment = "数据最后修改时间")
    var lastModifiedTime: Instant? = null

    /** 数据删除时间，为 null 表示未删除 */
    @Column(comment = "数据删除时间，为 null 表示未删除")
    var deletedTime: Instant? = null
}

/**
 * # 标记实体
 *
 * 用作标记，例如角色、权限、标签等
 */
@MappedSuperclass
abstract class FlagPO : BasePO()
{
    /** 标记 */
    @Size(max = 255)
    @Column(nullable = false, comment = "标记")
    var flag: String? = null
}
