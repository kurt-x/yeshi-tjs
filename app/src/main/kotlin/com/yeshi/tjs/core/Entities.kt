package com.yeshi.tjs.core

import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.Instant

/** # 持久化对象基类
 *
 * 数据库相关名称规范：
 * - DT: Data Table 数据表
 * - AT: Association Table 关联表
 * - UK: Union Key 唯一键
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BasePO : Serializable
{
    /** 数据 ID */
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, comment = "数据 ID")
    var id: Long? = null

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
