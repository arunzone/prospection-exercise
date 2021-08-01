package com.prospection.coding.assignment

import com.prospection.coding.assignment.dto.paragraph.GrammarResult
import com.prospection.coding.assignment.dto.paragraph.ViolationsResult
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.util.AssertionErrors.assertEquals
import java.net.URI
import java.net.URISyntaxException


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssignmentApplicationTests(@Autowired val restTemplate: TestRestTemplate) {
    @LocalServerPort
    var randomServerPort = 0

    @Test
    @Throws(URISyntaxException::class)
    fun `should return valid paragraph analysis payload`() {
        val baseUrl = "http://localhost:$randomServerPort/api/analysis"
        val text = """
            Cufabiu maffas nonad in auguec finibu soliciu. Mauhis arcusu semihe ir digil quisam impediec es macir quisua nullac. Nullam quir poral ac merul!
            Doneca punar de us nequl placeran sincdun. Neqed nil ulrices es hiraec qua rempl lacuse hisaec ers!
            
        """.trimIndent()
        val request: HttpEntity<String> = HttpEntity<String>(text)
        val result: ResponseEntity<GrammarResult> = restTemplate.postForEntity(
            URI(baseUrl),
            request,
            GrammarResult::class.java
        )

        val grammarResult = GrammarResult(
            verbsCount = 10,
            nounsCount = 20,
            prepositionsCount = 10,
            violation = ViolationsResult(
                charactersCount = 0,
                wordsCount = 0,
                verbsCount = 0,
                sentencesCount = 0,
                paragraphSuffixesCount = 0,
                paragraphSentencesCount = 0
            )
        )
        assertEquals("Invalid response payload", grammarResult, result.body)
    }

}