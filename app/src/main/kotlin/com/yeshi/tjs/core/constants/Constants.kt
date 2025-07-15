package com.yeshi.tjs.core.constants

// ====================
//  常量
// ====================

import java.time.format.DateTimeFormatter

/** 项目运行环境：本地环境 */
const val ENV_LOCAL = "local"

/** 项目运行环境：开发环境 */
const val ENV_DEV = "dev"

/** 项目运行环境：测试环境 */
const val ENV_TEST = "test"

/** 项目运行环境：生产环境 */
const val ENV_PROD = "prod"

/** 默认本地 Ipv4 */
const val DEFAULT_LOCAL_IP4 = "127.0.0.1"

/** `date time` 格式模板 */
const val DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

/** `date` 格式模板 */
const val DATE_PATTERN = "yyyy-MM-dd"

/** `time` 格式模板 */
const val TIME_PATTERN = "HH:mm:ss"

/** `date time` 格式化工具 */
val DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_PATTERN)!!

/** `date` 格式化工具 */
val DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN)!!

/** `time` 格式化工具 */
val TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN)!!

/** 登录认证方式：密码 */
const val AUTH_TYPE_PASSWORD = "Password"

/** 登录认证方式：短信 */
const val AUTH_TYPE_SMS = "SMS"

/** 登录认证方式：邮件 */
const val AUTH_TYPE_EMAIL = "Email"

/** UUID 长度 */
const val UUID_SIZE = 36

/** 不带 `-` 的 UUID 长度 */
const val SIMPLE_UUID_SIZE = 32
