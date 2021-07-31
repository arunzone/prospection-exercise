package com.prospection.coding.assignment.validator

import org.springframework.stereotype.Component

@Component
class AlphabetValidator {
    fun isValid(word: String): Boolean {
        val regex = Regex("^[abcdefghijlmnopqurs.!]+$", RegexOption.IGNORE_CASE)
        return word matches regex
    }

}