package com.yeshi.tjs.core.constants

// ====================
//  正则常量
// ====================

/** 正则表达式：手机号 */
const val REGEX_PHONE = "^\\d{5,15}$"

/** 正则匹配器：手机号 */
val PHONE_PATTERN = Regex(REGEX_PHONE)

/** 正则表达式：邮箱 */
const val REGEX_EMAIL = "^[a-zA-Z0-9_%+]+(?:\\.[a-zA-Z0-9_%+]+)*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"

/** 正则匹配器：邮箱 */
val EMAIL_PATTERN = Regex(REGEX_EMAIL)

/** 正则表达式：用户名 */
const val REGEX_USER_NAME = "^\\p{L}[\\p{L}-_0-9]{0,17}$"

/** 正则匹配器：用户名 */
val USER_NAME_PATTERN = Regex(REGEX_USER_NAME)

/** 正则表达式：用户真实名称 */
const val REGEX_REAL_NAME = "^\\p{L}+$"

/** 正则匹配器：用户真实名称 */
val REAL_NAME_PATTERN = Regex(REGEX_REAL_NAME)

/** 正则表达式：用户密码 */
const val REGEX_USER_PASSWORD = "^(?=.*[a-zA-Z])(?=.*\\d)[\\w@$!%*?&]{8,}$"

/** 正则匹配器：用户密码 */
val USER_PASSWORD_PATTERN = Regex(REGEX_USER_PASSWORD)

/** 正则表达式：IPv4 */
const val REGEX_IP4 = "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.|$)){4}$"

/** 正则匹配器：IPv4 */
val IP4_PATTERN = Regex(REGEX_IP4)
