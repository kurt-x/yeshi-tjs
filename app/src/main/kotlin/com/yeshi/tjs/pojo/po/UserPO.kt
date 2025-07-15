package com.yeshi.tjs.pojo.po

import com.yeshi.tjs.core.BasePO
import com.yeshi.tjs.core.constants.*
import com.yeshi.tjs.util.UUID4
import com.yeshi.tjs.util.UUID7
import jakarta.persistence.*
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

/** # 用户表 */
@Entity(name = "User")
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint("uk_users_uuid", ["uuid"]),
        UniqueConstraint("uk_users_name", ["name", "deleted_time"]),
        UniqueConstraint("uk_users_phone", ["phone", "deleted_time"]),
        UniqueConstraint("uk_users_email", ["email", "deleted_time"]),
    ],

    comment = "用户表"
)
@SQLRestriction("deleted_time is null")
@SQLDelete(sql = "update #{#entityName} set deleted_time = current_timestamp where id = ?;")
class UserPO : BasePO()
{
    /** UUID, v7（不带横杠 -） */
    @Column(length = SIMPLE_UUID_SIZE, nullable = false, updatable = false, comment = "UUID, v7")
    var uuid: String? = null

    /** 用户名 */
    @Size(max = 255)
    @Pattern(regexp = REGEX_USER_NAME)
    @Column(nullable = false, comment = "用户名")
    var name: String? = null

    /** 密码（加密后的密文） */
    @Size(max = 500)
    @Column(nullable = false, comment = "密码（加密后的密文）")
    var password: String? = null

    /** 用于加密密码的密钥，随机 UUID */
    @Column(length = UUID_SIZE, nullable = false, updatable = false, comment = "用于加密密码的密钥，随机 UUID")
    var secureKey: String? = null

    /** 手机号 */
    @Size(max = 50)
    @Pattern(regexp = REGEX_PHONE)
    @Column(comment = "手机号")
    var phone: String? = null

    /** 邮箱 */
    @Size(max = 255)
    @Pattern(regexp = REGEX_EMAIL)
    @Column(comment = "邮箱")
    var email: String? = null

    /** 角色集 */
    @Size(min = 1)
    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn("user_id", foreignKey = ForeignKey(name = "fk_user_roles_user"))],
        inverseJoinColumns = [JoinColumn("role_id", foreignKey = ForeignKey(name = "fk_user_roles_role"))],
        comment = "角色集",
    )
    @SQLRestriction("deleted_time is null")
    private var roles: Set<RolePO>? = null

    /** 是否已锁定 */
    @Column(comment = "是否已锁定")
    var locked: Boolean? = null

    /** 是否可用 */
    @Column(comment = "是否可用")
    var enabled: Boolean? = null

    /** @throws IllegalArgumentException 手机号和邮箱同时为无效状态 */
    @PrePersist
    fun prePersist()
    {
        require(phone != null || email != null) { "必须有一个有效的手机号或邮箱！" }
        uuid = UUID7.toString().replace("_", "")
        secureKey = UUID4.toString()
    }
}
