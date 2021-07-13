package com.example.newsforest;

public class News {

    private String image;
    private String title;
    private String webUrl;
    private String authorName;


    public News(String title, String authorName, String image, String webUrl) {
        this.title = title;
        this.authorName = authorName;
        this.image = image;
        this.webUrl = webUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
