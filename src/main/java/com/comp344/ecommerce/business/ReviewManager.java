package com.comp344.ecommerce.business;

import com.comp344.ecommerce.dao.HibernateReviewRepository;
import com.comp344.ecommerce.domain.Review;
import com.comp344.ecommerce.utils.Page;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Byambatsog on 10/2/16.
 */
@Service("reviewService")
public class ReviewManager {

    @Autowired
    private HibernateReviewRepository reviewRepository;

    public void save(Review review) throws Exception {
        reviewRepository.save(review);
    }

    public Review get(Integer id) throws Exception {
        return reviewRepository.get(id);
    }

    public void delete(Integer id) throws Exception {
        Review review = reviewRepository.get(id);
        reviewRepository.delete(review);
    }

    public Page<Review> find(Integer productId, Integer customerId, String orderBy, int page, int size){
        return reviewRepository.find(productId, customerId, orderBy, page, size);
    }
}
