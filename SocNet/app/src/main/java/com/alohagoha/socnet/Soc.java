package com.alohagoha.socnet;

public class Soc {
    private String description;
    private int pictures;
    private boolean like;

    public Soc(String description, int pictures, boolean like) {
        this.description = description;
        this.pictures = pictures;
        this.like = like;
    }

    public int getPictures() {
        return pictures;
    }

    public String getDescription() {
        return description;
    }

    public boolean getLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
