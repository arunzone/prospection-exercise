package com.prospection.coding.assignment.validator

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

internal class VerbValidatorTest : StringSpec({
    val verbValidator = VerbValidator()
    "word with length 5 should return true" {
        verbValidator.isVerb("ufabd") shouldBe true
    }
    "word with length 4 should return true" {
        verbValidator.isVerb("ufad") shouldBe true
    }
    "past tense verb should return true" {
        verbValidator.isVerb("ufabd") shouldBe true
    }
    "present tense verb should return true" {
        verbValidator.isVerb("ufabr") shouldBe true
    }
    "future tense verb should return true" {
        verbValidator.isVerb("ufabl") shouldBe true
    }
    "word with length smaller than 4 should return false" {
        verbValidator.isVerb("ufd") shouldBe false
    }
    "word with length greater than 5 should return false" {
        verbValidator.isVerb("ufabced") shouldBe false
    }
    "word with length 5 not ends with l,r,d should return false" {
        verbValidator.isVerb("ufabc") shouldBe false
    }

})

internal class InvalidVerbTest : StringSpec({
    val verbValidator = VerbValidator()
    "word with length 5 not ends with l,r,d should return true" {
        verbValidator.isNotVerb("ufabc") shouldBe true
    }
    "word with length greater than 5 should return false" {
        verbValidator.isVerb("ufabced") shouldBe false
    }

    table(
        headers("input", "result"),
        row("ufabl",  false),
        row("ufabd",  false),
        row("ufabr", false),
        row("ufab", true),
        row("ufabe", true),
        row("ufd", false),
    ).forAll { input, result ->
        verbValidator.isNotVerb(input) shouldBe result
    }
})
