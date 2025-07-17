package com.yeshi.tjs.module.system

import com.yeshi.tjs.core.BaseRepository
import com.yeshi.tjs.pojo.po.PermissionPO
import com.yeshi.tjs.pojo.po.RolePO
import com.yeshi.tjs.pojo.po.UserPO
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

/** 用户数据映射 */
@Mapper interface UserMapper

/** 用户数据接口 */
@Repository interface UserRepository : BaseRepository<UserPO>

/** 角色数据接口 */
@Repository interface RoleRepository : BaseRepository<RolePO>

/** 权限数据接口 */
@Repository interface PermissionRepository : BaseRepository<PermissionPO>
