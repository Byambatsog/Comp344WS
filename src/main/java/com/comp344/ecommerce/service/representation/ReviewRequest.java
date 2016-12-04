package com.comp344.ecommerce.service.representation;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class ReviewRequest {

    private String title;
    private String comment;
    private Integer rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
