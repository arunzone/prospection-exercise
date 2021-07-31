package com.prospection.coding.assignment.validator

class AlphabetValidator {
    fun validate(word: String): Boolean {
        val regex = Regex("^[abcdefghijlmnopqurs.!]+$", RegexOption.IGNORE_CASE)
        return word matches regex
    }

}