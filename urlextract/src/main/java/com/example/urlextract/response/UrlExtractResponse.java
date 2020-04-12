package com.example.urlextract.response;

import java.util.HashMap;
import java.util.Map;

public class UrlExtractResponse {


    String link;

    String total_links;

    Map<String , Integer> childs = new HashMap<>();

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTotal_links() {
        return total_links;
    }

    public void setTotal_links(String total_links) {
        this.total_links = total_links;
    }

    public Map<String, Integer> getChilds() {
        return childs;
    }

    public void setChilds(Map<String, Integer> childs) {
        this.childs = childs;
    }
}
