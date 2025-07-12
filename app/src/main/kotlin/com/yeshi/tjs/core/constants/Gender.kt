package com.yeshi.tjs.core.constants

import jakarta.persistence.AttributeConverter

/** # 性别 */
enum class Gender
{
    /** 男性 */
    MALE,

    /** 女性 */
    FEMALE,

    /** 第三性别 */
    THIRD,

    /** 保密 */
    SECURITY,

    /** 未知 */
    UNKNOWN,
    ;

    /** # 标记
     *
     * **首字母** */
    val flag = name[0]

    @jakarta.persistence.Converter
    class Converter : AttributeConverter<Gender, Char>
    {
        override fun convertToDatabaseColumn(attribute: Gender?) = attribute?.flag ?: UNKNOWN.flag

        override fun convertToEntityAttribute(dbData: Char?) =
            Gender.entries.firstOrNull { dbData == it.flag } ?: UNKNOWN
    }

}