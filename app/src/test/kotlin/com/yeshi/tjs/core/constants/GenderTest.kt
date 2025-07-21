package com.yeshi.tjs.core.constants

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GenderTest {
    private val converter = Gender.Converter()

    @Test
    fun convertToDatabaseColumn() {
        assertEquals('M', converter.convertToDatabaseColumn(Gender.MALE))
    }

    @Test
    fun convertToEntityAttribute() {
        assertEquals(Gender.FEMALE, converter.convertToEntityAttribute('F'))
    }
}
