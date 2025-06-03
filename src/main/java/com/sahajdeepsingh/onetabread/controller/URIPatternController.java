package com.sahajdeepsingh.onetabread.controller;

import com.sahajdeepsingh.onetabread.service.URIPatternService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend to access
@RestController
public class URIPatternController {
    // all 4 types needed
    // post is for posting a new pattern for a book
    // update is for changing the pattern of the book, history will bee kept
    // get is for comparing every url with it to know if history needs to record
    // delete is probably when book gets deleted

    private final URIPatternService uriPatternService;

    public URIPatternController(URIPatternService uriPatternService) {
        this.uriPatternService = uriPatternService;
    }
}
