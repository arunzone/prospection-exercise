package com.prospection.coding.assignment.service

import com.prospection.coding.assignment.dto.paragraph.GrammarResult
import com.prospection.coding.assignment.dto.paragraph.ViolationsResult
import com.prospection.coding.assignment.processor.ParagraphProcessor
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK

internal class LanguageServiceTest : ShouldSpec() {
    @MockK
    lateinit var paragraphProcessor: ParagraphProcessor

    private lateinit var languageService: LanguageService
    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        MockKAnnotations.init(this)
        languageService = LanguageService(paragraphProcessor)
    }

    init {
        context("single paragraph no linebreak") {
            val text = """
                Cufabiu maffas nonad in auguec finibu soliciu. Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. Nullam quir poral ac merul!
            """.trimIndent()

            val result = languageService.analyseGrammar(text)

            should("have empty result with one error") {
                result shouldBe GrammarResult(
                    verbsCount = 0,
                    nounsCount = 0,
                    prepositionsCount = 0,
                    violation = ViolationsResult(
                        charactersCount = 0,
                        wordsCount = 0,
                        sentencesCount = 0,
                        paragraphSentencesCount = 0,
                        paragraphSuffixesCount = 1
                    )
                )
            }
        }

        context("single paragraph") {
            val text = """
                Cufabiu maffas nonad in auguec finibu soliciu. Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. Nullam quir poral ac merul!
                
            """.trimIndent()
            val violationsResult = ViolationsResult(
                charactersCount = 4,
                wordsCount = 5,
                sentencesCount = 6,
                paragraphSuffixesCount = 0,
                paragraphSentencesCount = 1
            )
            val grammarResult = GrammarResult(
                violation = violationsResult,
                nounsCount = 1,
                verbsCount = 2,
                prepositionsCount = 3
            )
            every { paragraphProcessor process any() } answers { grammarResult }

            val result = languageService.analyseGrammar(text)

            should("have verbs count") {
                result.verbsCount shouldBe 2
            }
            should("have nouns count") {
                result.nounsCount shouldBe 1
            }
            should("have prepositions count") {
                result.prepositionsCount shouldBe 3
            }
            should("have character violation count") {
                result.violation.charactersCount shouldBe 4
            }
            should("have words violation count") {
                result.violation.wordsCount shouldBe 5
            }
            should("have sentence violation count") {
                result.violation.sentencesCount shouldBe 6
            }
            should("have paragraph sentence violation count") {
                result.violation.paragraphSentencesCount shouldBe 1
            }
            should("have paragraph suffix violation count") {
                result.violation.paragraphSuffixesCount shouldBe 0
            }
        }

        context("multiple paragraphs") {
            val text = """
                Cufabiu maffas nonad in auguec finibu soliciu. Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. Nullam quir poral ac merul!
                Doneca punar de us nequl placeran sincdun. Neqed nil ulrices es hiraec qua rempl lacuse hisaec ers!
                
            """.trimIndent()
            val violationsResult = ViolationsResult(
                charactersCount = 4,
                wordsCount = 5,
                sentencesCount = 6,
                paragraphSuffixesCount = 0,
                paragraphSentencesCount = 1
            )
            val grammarResult = GrammarResult(
                violation = violationsResult,
                nounsCount = 1,
                verbsCount = 2,
                prepositionsCount = 3
            )
            every { paragraphProcessor process any() } answers { grammarResult }

            val result = languageService.analyseGrammar(text)

            should("have verbs count") {
                result.verbsCount shouldBe 4
            }
            should("have nouns count") {
                result.nounsCount shouldBe 2
            }
            should("have prepositions count") {
                result.prepositionsCount shouldBe 6
            }
            should("have character violation count") {
                result.violation.charactersCount shouldBe 8
            }
            should("have words violation count") {
                result.violation.wordsCount shouldBe 10
            }
            should("have sentence violation count") {
                result.violation.sentencesCount shouldBe 12
            }
            should("have paragraph sentence violation count") {
                result.violation.paragraphSentencesCount shouldBe 2
            }
            should("have paragraph suffix violation count") {
                result.violation.paragraphSuffixesCount shouldBe 0
            }
        }

        context("invalid paragraphs") {
            val text = """
                Cufabiu maffas nonad in auguec finibu soliciu. Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. Nullam quir poral ac merul!
            """.trimIndent()
            val textOneLineBreak = """
                Cufabiu maffas nonad in auguec finibu soliciu. Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. Nullam quir poral ac merul!
                
            """.trimIndent()
            val textTwoLineBreak = """
                Cufabiu maffas nonad in auguec finibu soliciu. Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. Nullam quir poral ac merul!
                
                
            """.trimIndent()
            val invalidParagrapLineBreak = """
                Cufabiu maffas nonad in auguec finibu soliciu.
                Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. Nullam quir poral ac merul!
                
            """.trimIndent()
            val violationsResult = ViolationsResult(
                charactersCount = 4,
                wordsCount = 5,
                sentencesCount = 6,
                paragraphSuffixesCount = 0,
                paragraphSentencesCount = 1
            )
            val grammarResult = GrammarResult(
                violation = violationsResult,
                nounsCount = 1,
                verbsCount = 2,
                prepositionsCount = 3
            )
            every { paragraphProcessor process any() } answers { grammarResult }

            table(
                headers("input", "errorCount"),
                row(text,  1),
                row(textOneLineBreak,  0),
                row(textTwoLineBreak, 1),
                row(invalidParagrapLineBreak, 1),
            ).forAll { input, errorCount ->
                languageService.analyseGrammar(input).violation.paragraphSuffixesCount shouldBe errorCount
            }
        }


    }
}