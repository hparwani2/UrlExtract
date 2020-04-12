package com.example.urlextract.service;

import com.example.urlextract.response.UrlExtractResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UrlExtractService {

    ExecutorService executorService = Executors.newFixedThreadPool(20);

    public UrlExtractResponse ExtractLinks(String Url , int depth) {
        UrlExtractResponse urlExtractResponse = new UrlExtractResponse();
        try {

            Map<String, List<String>> map = new ConcurrentHashMap<>();
            Set<String> visited = ConcurrentHashMap.newKeySet();
            Map<String, Integer> result = new ConcurrentHashMap<>();
            ExtractLinksUtil(Url, depth, map, visited, result);
            Thread.sleep(8000);
            urlExtractResponse.setLink(Url);
            urlExtractResponse.setTotal_links(String.valueOf(result.size()));
            urlExtractResponse.setChilds(result);
        }
        catch (Exception e) {

        }

        return urlExtractResponse;
    }

    public void ExtractLinksUtil(String link, int depth, Map<String, List<String>> map, Set<String> visited , Map<String,Integer> results) {
        try {


            if (visited.contains(link))
                return;

            if (depth == 0)
                return;

            List<String> childs = map.get(link);
            if (childs == null) {
                visited.add(link);
                Document doc = Jsoup.connect(link).userAgent("Chrome").get();
                Elements links = doc.select("a[href]");
                Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                int imageSize = images.size();
                results.put(link, imageSize);
                childs = new ArrayList<>();
                for (Element e : links) {
                    childs.add(e.attr("abs:href"));
                }
                map.put(link, childs);
                for (String child : childs) {
                    try {
                        executorService.submit(() -> {
                            ExtractLinksUtil(child, depth - 1, map, visited, results);
                        });
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }

            } else {
                for (String child : childs) {
                    ExtractLinksUtil(child, depth - 1, map, visited, results);
                }
            }
        }
        catch (Exception e) {

        }
    }
}
