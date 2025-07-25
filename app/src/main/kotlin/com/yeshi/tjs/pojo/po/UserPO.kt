package com.yeshi.tjs.pojo.po

import com.yeshi.tjs.core.BasePO
import com.yeshi.tjs.core.constants.REGEX_EMAIL
import com.yeshi.tjs.core.constants.REGEX_PHONE
import com.yeshi.tjs.core.constants.REGEX_USER_NAME
import com.yeshi.tjs.core.constants.UUID_SIZE
import com.yeshi.tjs.util.UUID4
import jakarta.persistence.*
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.hibernate.annotations.UuidGenerator
import java.util.*

/** # 用户表 */
@Entity(name = "User")
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["name", "deleted_time"]),
        UniqueConstraint(columnNames = ["phone", "deleted_time"]),
        UniqueConstraint(columnNames = ["email", "deleted_time"]),
    ],

    comment = "用户表"
)
@SQLRestriction("deleted_time is null")
@SQLDelete(sql = "update #{#entityName} set deleted_time = current_timestamp where id = ?;")
class UserPO : BasePO()
{
    /** 数据 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(nullable = false, updatable = false, comment = "数据 ID")
    var id: UUID? = null

    /** 用户名 */
    @Pattern(regexp = REGEX_USER_NAME)
    @Column(length = 50, nullable = false, comment = "用户名")
    var name: String? = null

    /** 密码（加密后的密文） */
    @Column(nullable = false, comment = "密码（加密后的密文）")
    var password: String? = null

    /** 用于加密密码的密钥，随机 UUID */
    @Column(
        nullable = false, updatable = false,
        comment = "用于加密密码随机 UUID 秘钥", columnDefinition = "char($UUID_SIZE)"
    )
    var secureKey: String? = null

    /** 手机号 */
    @Pattern(regexp = REGEX_PHONE)
    @Column(length = 50, comment = "手机号")
    var phone: String? = null

    /** 邮箱 */
    @Pattern(regexp = REGEX_EMAIL)
    @Column(comment = "邮箱")
    var email: String? = null

    /** 角色集 */
    @Size(min = 1)
    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn("user_id")],
        inverseJoinColumns = [JoinColumn("role_id")],
        comment = "角色集",
    )
    @SQLRestriction("deleted_time is null")
    var roles: Set<RolePO>? = null

    /** 是否已锁定 */
    @Column(nullable = false, comment = "是否已锁定")
    var locked: Boolean? = null

    /** 是否可用 */
    @Column(nullable = false, comment = "是否可用")
    var enabled: Boolean? = null

    /** @throws IllegalArgumentException 手机号和邮箱同时为无效状态 */
    @PrePersist
    fun prePersist()
    {
        require(phone != null || email != null) { "必须有一个有效的手机号或邮箱！" }
        secureKey = UUID4.toString()
    }
}
