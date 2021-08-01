package com.prospection.coding.assignment.controller;

import com.prospection.coding.assignment.dto.paragraph.GrammarResult
import com.prospection.coding.assignment.service.LanguageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/analysis") // must start with "/api/" so that front-end app can talk to
class AnalysisController(@Autowired private val languageService: LanguageService) {

    @PostMapping(consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun analysis(@RequestBody text: String): GrammarResult {
        return languageService analyseGrammar  text;
    }

}
