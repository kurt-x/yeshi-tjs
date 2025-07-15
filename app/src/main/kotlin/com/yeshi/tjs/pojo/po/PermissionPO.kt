package com.yeshi.tjs.pojo.po

import com.yeshi.tjs.core.FlagPO
import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
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
    /** 拥有该权限的角色集合 */
    @ManyToMany(mappedBy = "permissions")
    @SQLRestriction("deleted_time is null")
    var roles: Set<RolePO>? = null
}
