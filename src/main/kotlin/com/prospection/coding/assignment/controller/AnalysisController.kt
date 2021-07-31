package com.prospection.coding.assignment.controller;

import com.prospection.coding.assignment.processor.SentenceProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/analysis") // must start with "/api/" so that front-end app can talk to
class AnalysisController(@Autowired private val sentenceProcessor: SentenceProcessor) {

    @PostMapping(consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun analysis(@RequestBody text: String): GrammarResult {
        return sentenceProcessor.process(text);
    }

}
