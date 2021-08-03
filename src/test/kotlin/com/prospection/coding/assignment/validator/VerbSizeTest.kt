package com.prospection.coding.assignment.validator

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

internal class VerbSizeTest : StringSpec({
    val verbValidator = VerbValidator()
    "word with length 5 should return true" {
        verbValidator.verbSized("ufabd") shouldBe true
    }
    "word with length 4 should return true" {
        verbValidator.verbSized("ufad") shouldBe true
    }
    "word with length smaller than 4 should return false" {
        verbValidator.verbSized("ufd") shouldBe false
    }
    "word with length greater than 5 should return false" {
        verbValidator.verbSized("ufabced") shouldBe false
    }
})

internal class ValidVerbTest : StringSpec({
    val verbValidator = VerbValidator()
    table(
        headers("input", "result"),
        row("ufabl",  true),
        row("ufabd",  true),
        row("ufabr", true),
        row("ufar", true),
        row("ufal", true),
        row("ufad", true),
    ).forAll { input, result ->
        verbValidator.isVerb(input) shouldBe result
    }
})
internal class InvalidVerbTest : StringSpec({
    val verbValidator = VerbValidator()
    table(
        headers("input", "result"),
        row("ufae", false),
        row("ufabe", false),
        ).forAll { input, result ->
        verbValidator.isVerb(input) shouldBe result
    }
})
