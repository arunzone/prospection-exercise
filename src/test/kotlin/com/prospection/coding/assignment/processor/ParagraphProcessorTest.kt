package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.dto.GrammarResult
import com.prospection.coding.assignment.dto.ViolationsResult
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK

internal class ParagraphProcessorTest : ShouldSpec() {
    @MockK
    lateinit var sentenceProcessor: SentenceProcessor

    @MockK
    lateinit var grammarResult: GrammarResult
    lateinit var paragraphProcessor: ParagraphProcessor

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        MockKAnnotations.init(this)
        paragraphProcessor = ParagraphProcessor(sentenceProcessor)
    }

    init {
        context("paragraph has single sentence") {
            every { sentenceProcessor process "Cufabiu maffas nonad in auguec finibu soliciu" } answers { grammarResult }
            should("return a sentence result") {
                val result =
                    paragraphProcessor process "Cufabiu maffas nonad in auguec finibu soliciu."
                result shouldBe grammarResult
            }
        }

        context("paragraph has multiple sentence") {
            val emptyViolationsResult = ViolationsResult(
                charactersCount = 0,
                wordsCount = 0,
                sentencesCount = 0
            )
            context("verb") {
                val firstResult = GrammarResult(
                    verbsCount = 1,
                    nounsCount = 0,
                    prepositionsCount = 0,
                    violations = emptyViolationsResult
                )
                val secondResult = GrammarResult(
                    verbsCount = 2,
                    nounsCount = 0,
                    prepositionsCount = 0,
                    violations = emptyViolationsResult
                )
                every { sentenceProcessor process "Cufabiu maffas nonad in auguec finibu soliciu" } answers { firstResult }
                every { sentenceProcessor process "Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac" } answers { secondResult }
                should("return sum of verbs") {
                    val result =
                        paragraphProcessor process "Cufabiu maffas nonad in auguec finibu soliciu. Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. "
                    result shouldBe GrammarResult(
                        verbsCount = 3,
                        nounsCount = 0,
                        prepositionsCount = 0,
                        violations = emptyViolationsResult
                    )
                }
            }

            context("noun") {
                val firstResult = GrammarResult(
                    verbsCount = 0,
                    nounsCount = 2,
                    prepositionsCount = 0,
                    violations = emptyViolationsResult
                )
                val secondResult = GrammarResult(
                    verbsCount = 0,
                    nounsCount = 3,
                    prepositionsCount = 0,
                    violations = emptyViolationsResult
                )
                every { sentenceProcessor process "Cufabiu maffas nonad in auguec finibu soliciu" } answers { firstResult }
                every { sentenceProcessor process "Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac" } answers { secondResult }
                should("return sum of nouns") {
                    val result =
                        paragraphProcessor process "Cufabiu maffas nonad in auguec finibu soliciu. Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. "
                    result shouldBe GrammarResult(
                        verbsCount = 0,
                        nounsCount = 5,
                        prepositionsCount = 0,
                        violations = emptyViolationsResult
                    )
                }
            }

            context("preposition") {
                val firstResult = GrammarResult(
                    verbsCount = 0,
                    nounsCount = 0,
                    prepositionsCount = 3,
                    violations = emptyViolationsResult
                )
                val secondResult = GrammarResult(
                    verbsCount = 0,
                    nounsCount = 0,
                    prepositionsCount = 4,
                    violations = emptyViolationsResult
                )
                every { sentenceProcessor process "Cufabiu maffas nonad in auguec finibu soliciu" } answers { firstResult }
                every { sentenceProcessor process "Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac" } answers { secondResult }
                should("return sum of prepositions") {
                    val result =
                        paragraphProcessor process "Cufabiu maffas nonad in auguec finibu soliciu. Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. "
                    result shouldBe GrammarResult(
                        verbsCount = 0,
                        nounsCount = 0,
                        prepositionsCount = 7,
                        violations = emptyViolationsResult
                    )
                }
            }
        }

    }
}