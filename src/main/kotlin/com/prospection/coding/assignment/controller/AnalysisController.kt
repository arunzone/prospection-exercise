package com.prospection.coding.assignment.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/analysis") // must start with "/api/" so that front-end app can talk to
class AnalysisController {

    @PostMapping(consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun analysis(@RequestBody text: String?): String? {
        // TODO: modified to complete API
        return text;
    }

}
