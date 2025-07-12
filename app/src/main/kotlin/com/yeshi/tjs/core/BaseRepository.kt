package com.yeshi.tjs.core

import org.springframework.data.jpa.repository.JpaRepository

/** # [Repository][org.springframework.data.repository.Repository] 基类 */
interface BaseRepository<PO : BasePO> : JpaRepository<PO, Long>
