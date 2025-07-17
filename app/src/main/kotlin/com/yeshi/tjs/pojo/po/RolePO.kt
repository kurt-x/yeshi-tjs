package com.yeshi.tjs.pojo.po

import com.yeshi.tjs.core.FlagPO
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

/** # 角色表 */
@Entity(name = "Role")
@Table(
    name = "roles",
    uniqueConstraints = [UniqueConstraint(columnNames = ["flag", "deleted_time"])],
    comment = "角色表"
)
@SQLRestriction("deleted_time is null")
@SQLDelete(sql = "update #{#entityName} set deleted_time = current_timestamp where id = ?;")
class RolePO : FlagPO()
{
    /** 数据 ID */
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, comment = "数据 ID")
    var id: Long? = null

    /** 拥有该角色的用户集合 */
    @ManyToMany(mappedBy = "roles")
    @SQLRestriction("deleted_time is null")
    var users: Set<UserPO>? = null

    /** 权限集 */
    @ManyToMany
    @JoinTable(
        name = "role_permissions",
        joinColumns = [JoinColumn("role_id")],
        inverseJoinColumns = [JoinColumn("permission_id")],
        comment = "权限集",
    )
    @SQLRestriction("deleted_time is null")
    var permissions: Set<PermissionPO>? = null
}
