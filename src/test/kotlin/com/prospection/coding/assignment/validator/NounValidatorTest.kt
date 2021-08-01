package com.prospection.coding.assignment.validator

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class NounValidatorTest : StringSpec({
    val grammarValidator = GrammarValidator()
    "isNoun should return true for a word size of 6"{
        grammarValidator.isNoun("abcdef") shouldBe true
    }
    "isNoun should return true for a word size of 7"{
        grammarValidator.isNoun("abcdefg") shouldBe true
    }
    "isNoun should return true for a word size of 8"{
        grammarValidator.isNoun("abcdefgh") shouldBe true
    }
    "isNoun should return false for a word size of 9"{
        grammarValidator.isNoun("abcdefghi") shouldBe false
    }
    "isNoun should return false for a word size of 5"{
        grammarValidator.isNoun("abcde") shouldBe false
    }
})

internal class PrepositionValidatorTest : StringSpec({
    val grammarValidator = GrammarValidator()
    "isPreposition should return true for a word size of 1"{
        grammarValidator.isPreposition("a") shouldBe true
    }
    "isPreposition should return true for a word size of 2"{
        grammarValidator.isPreposition("ab") shouldBe true
    }
    "isPreposition should return true for a word size of 3"{
        grammarValidator.isPreposition("abc") shouldBe true
    }
    "isPreposition should return false for a word size of 4"{
        grammarValidator.isPreposition("abcd") shouldBe false
    }

})