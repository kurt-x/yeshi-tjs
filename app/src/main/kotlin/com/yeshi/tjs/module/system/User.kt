package com.yeshi.tjs.module.system

import com.yeshi.tjs.core.BaseRepository
import com.yeshi.tjs.pojo.po.PermissionPO
import com.yeshi.tjs.pojo.po.RolePO
import com.yeshi.tjs.pojo.po.UserPO

/** 用户数据接口 */
interface UserRepository : BaseRepository<UserPO>

/** 角色数据接口 */
interface RoleRepository : BaseRepository<RolePO>

/** 权限数据接口 */
interface PermissionRepository : BaseRepository<PermissionPO>
