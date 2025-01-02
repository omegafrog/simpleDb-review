package org.example;

import java.time.LocalDateTime;

public class Article {
    private  Long id;
    private  String title;
    private  String body;
    private  LocalDateTime createdDate;
    private  LocalDateTime modifiedDate;
    private  boolean blind;

    private Article(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.body = builder.body;
        this.createdDate = builder.createdDate;
        this.modifiedDate = builder.modifiedDate;
        this.blind = builder.blind;
    }
    private Article (){}

    private final static class Builder{
        private  Long id;
        private  String title;
        private  String body;
        private  LocalDateTime createdDate;
        private  LocalDateTime modifiedDate;
        private  boolean blind;

        public Article build(){
            return new Article(this);
        }
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        public Builder body(String body) {
            this.body = body;
            return this;
        }
        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }
        public Builder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }
        public Builder blind(boolean blind){
            this.blind = blind;
            return this;
        }
    }
    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public boolean isBlind() {
        return this.blind;
    }
}
