package com.example.urlextract.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class UrlExtractResponse {

    @JsonProperty("total_links")
    String totalLinks;

    @JsonProperty("total_images")
    String totalImages;

    @JsonProperty("details")
    private Set<PageVO> pageVOList;

    public String getTotalLinks() {
        return totalLinks;
    }

    public void setTotalLinks(String totalLinks) {
        this.totalLinks = totalLinks;
    }

    public String getTotalImages() {
        return totalImages;
    }

    public void setTotalImages(String totalImages) {
        this.totalImages = totalImages;
    }

    public Set<PageVO> getPageVOList() {
        return pageVOList;
    }

    public void setPageVOList(Set<PageVO> pageVOList) {
        this.pageVOList = pageVOList;
    }
}
