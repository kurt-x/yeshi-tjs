package com.yeshi.tjs.module.system

import com.yeshi.tjs.pojo.po.RolePO
import com.yeshi.tjs.pojo.po.UserPO
import org.apache.ibatis.annotations.Mapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/** # 用户 */
class User
{
    lateinit var id: UUID
    lateinit var name: String
}

/** 用户数据映射 */
@Mapper interface UserMapper

/** 用户数据接口 */
@Repository interface UserRepository : JpaRepository<UserPO, UUID>

/** 角色数据接口 */
@Repository interface RoleRepository : JpaRepository<RolePO, Long>

/** 权限数据接口 */
@Repository interface PermissionRepository : JpaRepository<RolePO, Long>
