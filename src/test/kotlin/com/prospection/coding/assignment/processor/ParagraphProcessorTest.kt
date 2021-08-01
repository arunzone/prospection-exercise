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
            val firstResult = GrammarResult(
                verbsCount = 1,
                nounsCount = 2,
                prepositionsCount = 3,
                violations = emptyViolationsResult
            )
            val secondResult = GrammarResult(
                verbsCount = 2,
                nounsCount = 3,
                prepositionsCount = 4,
                violations = emptyViolationsResult
            )
            every { sentenceProcessor process "Cufabiu maffas nonad in auguec finibu soliciu" } answers { firstResult }
            every { sentenceProcessor process "Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac" } answers { secondResult }
            val result =
                paragraphProcessor process "Cufabiu maffas nonad in auguec finibu soliciu. Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. "

            should("return sum of verbs") {
                result.verbsCount shouldBe 3
            }
            should("return sum of nouns") {
                result.nounsCount shouldBe 5
            }
            should("return sum of prepositions") {
                result.prepositionsCount shouldBe 7
            }

            context("violation") {
                val firstViolationsResult = ViolationsResult(
                    charactersCount = 1,
                    wordsCount = 2,
                    sentencesCount = 3
                )
                val secondViolationsResult = ViolationsResult(
                    charactersCount = 4,
                    wordsCount = 5,
                    sentencesCount = 6
                )
                val firstResult = GrammarResult(
                    verbsCount = 0,
                    nounsCount = 0,
                    prepositionsCount = 0,
                    violations = firstViolationsResult
                )
                val secondResult = GrammarResult(
                    verbsCount = 0,
                    nounsCount = 0,
                    prepositionsCount = 0,
                    violations = secondViolationsResult
                )
                every { sentenceProcessor process "Cufabiu maffas nonad in auguec finibu soliciu" } answers { firstResult }
                every { sentenceProcessor process "Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac" } answers { secondResult }
                should("return sum of violations") {
                    val result =
                        paragraphProcessor process "Cufabiu maffas nonad in auguec finibu soliciu. Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. "
                    result shouldBe GrammarResult(
                        verbsCount = 0,
                        nounsCount = 0,
                        prepositionsCount = 0,
                        violations = ViolationsResult(
                            charactersCount = 5,
                            wordsCount = 7,
                            sentencesCount = 9
                        )
                    )
                }
            }
        }

    }
}