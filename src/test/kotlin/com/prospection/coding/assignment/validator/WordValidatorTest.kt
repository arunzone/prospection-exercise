package com.prospection.coding.assignment.validator

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class WordValidatorTest : StringSpec({
    val wordValidator = WordValidator()
    "a word size of 8 should return true"{
        wordValidator.isWord("auguecul") shouldBe true
    }
    "a word size lesser than 8 should return false"{
        wordValidator.isWord("aug") shouldBe true
    }
    "a word size greater than 8 should return false"{
        wordValidator.isWord("augueculrices") shouldBe false
    }
})