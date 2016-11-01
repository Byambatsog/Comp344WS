package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.Review;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class ReviewRepresentation {

    private Integer id;
    private String title;
    private String comment;
    private Integer rating;
    private String customerName;

    public ReviewRepresentation(){}

    public ReviewRepresentation(Review review){
        this.id = review.getId();
        this.title = review.getTitle();
        this.comment = review.getComment();
        this.rating = review.getRating();
        this.customerName = review.getCustomer().getFirstName();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public Integer getRating() {
        return rating;
    }

    public String getCustomerName() {
        return customerName;
    }
}
