package com.prospection.coding.assignment.processor

import com.prospection.coding.assignment.dto.paragraph.GrammarResult
import com.prospection.coding.assignment.dto.paragraph.ViolationsResult
import com.prospection.coding.assignment.dto.sentence.SentenceGrammarResult
import com.prospection.coding.assignment.dto.sentence.SentenceViolationsResult
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK

internal class ParagraphProcessorTest : ShouldSpec() {
    @MockK
    lateinit var sentenceProcessor: SentenceProcessor

    private lateinit var paragraphProcessor: ParagraphProcessor

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        MockKAnnotations.init(this)
        paragraphProcessor = ParagraphProcessor(sentenceProcessor)
    }

    init {
        context("paragraph has single sentence") {
            val sentenceViolationsResult = SentenceViolationsResult(
                charactersCount = 1,
                wordsCount = 2,
                sentencesCount = 3
            )
            val sentenceGrammarResult = SentenceGrammarResult(
                verbsCount = 1,
                nounsCount = 2,
                prepositionsCount = 3,
                violation = sentenceViolationsResult
            )
            every { sentenceProcessor process "Cufabiu maffas nonad in auguec finibu soliciu" } answers { sentenceGrammarResult }
            should("return a sentence result") {
                val result =
                    paragraphProcessor process "Cufabiu maffas nonad in auguec finibu soliciu."
                result shouldBe GrammarResult(
                    verbsCount = 1,
                    nounsCount = 2,
                    prepositionsCount = 3,
                    violation = ViolationsResult(
                        charactersCount = 1,
                        wordsCount = 2,
                        sentencesCount = 3,
                        paragraphSuffixesCount = 0,
                        paragraphSentencesCount = 0
                    )
                )
            }
        }

        context("paragraph has multiple sentence") {
            context("verbs, noun and preposition") {
                val firstSentenceViolationsResult = SentenceViolationsResult(
                    charactersCount = 1,
                    wordsCount = 2,
                    sentencesCount = 3
                )
                val secondSentenceViolationsResult = SentenceViolationsResult(
                    charactersCount = 4,
                    wordsCount = 5,
                    sentencesCount = 6
                )
                val firstResult = SentenceGrammarResult(
                    verbsCount = 1,
                    nounsCount = 2,
                    prepositionsCount = 3,
                    violation = firstSentenceViolationsResult
                )
                val secondResult = SentenceGrammarResult(
                    verbsCount = 2,
                    nounsCount = 3,
                    prepositionsCount = 4,
                    violation = secondSentenceViolationsResult
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
                should("return sum of violation") {
                    result.violation shouldBe ViolationsResult(
                        charactersCount = 5,
                        wordsCount = 7,
                        sentencesCount = 9,
                        paragraphSuffixesCount = 0,
                        paragraphSentencesCount = 0
                    )
                }
            }
            context("paragraph violation") {
                val sentenceViolationsResult = SentenceViolationsResult(
                    charactersCount = 1,
                    wordsCount = 2,
                    sentencesCount = 3
                )
                val sentenceGrammarResult = SentenceGrammarResult(
                    verbsCount = 1,
                    nounsCount = 2,
                    prepositionsCount = 3,
                    violation = sentenceViolationsResult
                )

                every { sentenceProcessor process any() } answers { sentenceGrammarResult }
                val result =
                    paragraphProcessor process "Vivamus libero enim, sagittis at nulla in, malesuada pretium nunc. Donec punar de, ut nequl placeran tincdun. Neqed nil ulrices es. Vitae qua templ lacuse vitaec est"

                should("return sum of violation") {
                    result.violation.paragraphSentencesCount shouldBe 1
                }
            }

        }

    }
}