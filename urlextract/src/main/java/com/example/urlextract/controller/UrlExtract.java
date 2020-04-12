package com.example.urlextract.controller;

import com.example.urlextract.response.UrlExtractResponse;
import com.example.urlextract.service.UrlExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller("/api")
public class UrlExtract {

    @Autowired
    private UrlExtractService urlExtractService;

    @RequestMapping(value = "/extracturl", method = RequestMethod.GET)
    public @ResponseBody  UrlExtractResponse ExtractUrls(@RequestParam("url") String url
            , @RequestParam("depth") int depth) {

        Map<String, List<String>> map = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Map<String,Integer> result = new HashMap<>();
        return urlExtractService.ExtractLinks(url, depth);

    }

}
