package com.cyclicsoft.bookreview.models;

public class Book {
    String id;
    String title;
    String author;
    String sum;
    String url;
    double ratings = 0;

    public Book(String id) {
        this.id = id;
    }

    public Book(String title, String author, String sum, String url, long ratings) {
        this.title = title;
        this.author = author;
        this.sum = sum;
        this.url = url;
        this.ratings = ratings;
    }

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", sum='" + sum + '\'' +
                ", url='" + url + '\'' +
                ", ratings=" + ratings +
                '}';
    }
}

