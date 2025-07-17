package com.yeshi.tjs.pojo.po

import com.yeshi.tjs.core.FlagPO
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

/** # 权限表 */
@Entity(name = "Permission")
@Table(
    name = "permissions",
    uniqueConstraints = [UniqueConstraint("uk_permissions_flag", ["flag", "deleted_time"])],
    comment = "权限表"
)
@SQLRestriction("deleted_time is null")
@SQLDelete(sql = "update #{#entityName} set deleted_time = current_timestamp where id = ?;")
class PermissionPO : FlagPO()
{
    /** 数据 ID */
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, comment = "数据 ID")
    var id: Long? = null

    /** 拥有该权限的角色集合 */
    @ManyToMany(mappedBy = "permissions")
    @SQLRestriction("deleted_time is null")
    var roles: Set<RolePO>? = null
}
