package com.prospection.coding.assignment.validator

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class AlphabetValidatorTest : StringSpec({
    val alphabetValidator = AlphabetValidator()
    "word with all small case word should return true" {
        alphabetValidator.isValid("ufabiu") shouldBe true
    }
    "word starts with upper case should return true" {
        alphabetValidator.isValid("Cufabiu") shouldBe true
    }
    "word ends with dot should return true" {
        alphabetValidator.isValid("soliciu.") shouldBe true
    }
    "word ends with exclamation should return true" {
        alphabetValidator.isValid("ers!") shouldBe true
    }
    "word with unaccepted character should return false" {
        alphabetValidator.isValid("Nunddz") shouldBe false
    }
})