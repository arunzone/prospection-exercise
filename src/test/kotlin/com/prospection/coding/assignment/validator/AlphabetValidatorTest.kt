package com.prospection.coding.assignment.validator

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class AlphabetValidatorTest : StringSpec({
    val alphabetValidator = AlphabetValidator()
    "word with all small case word should return true" {
        alphabetValidator.validate("ufabiu") shouldBe true
    }
    "word starts with upper case should return true" {
        alphabetValidator.validate("Cufabiu") shouldBe true
    }
    "word ends with dot should return true" {
        alphabetValidator.validate("soliciu.") shouldBe true
    }
    "word ends with exclamation should return true" {
        alphabetValidator.validate("ers!") shouldBe true
    }
    "word with unaccepted character should return false" {
        alphabetValidator.validate("Nunddz") shouldBe false
    }
})