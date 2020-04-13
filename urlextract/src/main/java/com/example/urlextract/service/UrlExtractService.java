package com.example.urlextract.service;

import com.example.urlextract.response.PageVO;
import com.example.urlextract.response.UrlExtractResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UrlExtractService {

    MyThreadPoolExecutor executor = new MyThreadPoolExecutor(2000);
    private static int totalImages;

    public UrlExtractResponse extractLinks(URL url , int depth) {
        UrlExtractResponse urlExtractResponse = new UrlExtractResponse();
        try {
            Map<URL, Set<URL>> map = new ConcurrentHashMap<>();
            Set<URL> visited = ConcurrentHashMap.newKeySet();
            Set<PageVO> results = ConcurrentHashMap.newKeySet();
            extractLinksUtil(url, depth, map, visited, results);
            executor.waitForExecuted();
            urlExtractResponse.setTotalLinks(String.valueOf(results.size()));
            urlExtractResponse.setTotalImages(String.valueOf(totalImages));
            urlExtractResponse.setPageVOList(results);
        }
        catch (Exception e) {
            System.out.println("Some exception while extracting from main link: " + e);
        }

        return urlExtractResponse;
    }

    public void extractLinksUtil(URL link, int depth, Map<URL, Set<URL>> map, Set<URL> visited , Set<PageVO> results) {
        try {
            if (visited.contains(link))
                return;

            if (depth == 0)
                return;

            Set<URL> childs = map.get(link);
            if (childs == null) {
                visited.add(link);
                Document doc = Jsoup.parse(link, 10000);
                Elements titles = doc.select("title");
                Elements links = doc.select("a[href]");
                Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                PageVO pageVO = new PageVO();
                pageVO.setImageCount(images == null || images.isEmpty() ? 0 : images.size());
                totalImages+=pageVO.getImageCount();
                pageVO.setPageTitle(titles == null || titles.isEmpty() ? "" : titles.get(0).text());
                pageVO.setPageLink(link);
                results.add(pageVO);
                childs = ConcurrentHashMap.newKeySet();
                for (Element e : links) {
                    childs.add(new URL(e.attr("abs:href")));
                }
                map.put(link, childs);
                for (URL child : childs) {
                    try {
                        executor.execute(() -> extractLinksUtil(child, depth - 1, map, visited, results));
                    } catch (Exception e) {
                        System.out.println("Some exception while extracting child links: " + e);
                        continue;
                    }
                }

            }
        }
        catch (Exception e) {
            System.out.println("Some exception while extracting links: " + e);
        }
    }
}
