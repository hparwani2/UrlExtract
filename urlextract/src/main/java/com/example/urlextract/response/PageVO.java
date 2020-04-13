package com.example.urlextract.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;

public class PageVO {
    @JsonProperty("page_title")
    private String pageTitle;
    @JsonProperty("page_link")
    private URL pageLink;
    @JsonProperty("image_count")
    private int imageCount;

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public URL getPageLink() {
        return pageLink;
    }

    public void setPageLink(URL pageLink) {
        this.pageLink = pageLink;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }
}
