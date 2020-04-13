package com.example.urlextract.controller;

import com.example.urlextract.response.UrlExtractResponse;
import com.example.urlextract.service.UrlExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
@RequestMapping("/api")
public class UrlExtract {

    @Autowired
    private UrlExtractService urlExtractService;

    @GetMapping("/extractUrl")
    public UrlExtractResponse extractUrls(@RequestParam("url") URL url
            , @RequestParam("depth") int depth) {

        return urlExtractService.extractLinks(url, depth);

    }

}
