package com.comp344.ecommerce.service.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.comp344.ecommerce.business.CustomerManager;
import com.comp344.ecommerce.business.ReviewManager;
import com.comp344.ecommerce.domain.Review;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.service.representation.ProductDetailRepresentation;
import com.comp344.ecommerce.service.representation.ReviewRepresentation;
import com.comp344.ecommerce.service.representation.ReviewRequest;
import com.comp344.ecommerce.utils.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comp344.ecommerce.business.ProductManager;
import com.comp344.ecommerce.domain.Product;
import com.comp344.ecommerce.service.representation.ProductRepresentation;
import com.comp344.ecommerce.utils.Page;

@Component
public class ProductActivity {
	
	@Autowired
    private ProductManager productManager;

	@Autowired
	private ReviewManager reviewManager;

	@Autowired
	private CustomerManager customerManager;
	
	public Page<ProductRepresentation> findProducts(String searchQuery, Integer categoryId, Integer partnerId,
													String orderBy, int page, int pageSize) throws Exception{

		if(orderBy != null && !orderBy.equals("")){
			if (orderBy.equals("created")){
				orderBy = "order by created_at desc";
			} else if (orderBy.equals("name")){
				orderBy = "order by name asc";
			}
		}

		Page<Product> products = productManager.find(searchQuery, categoryId, partnerId, Boolean.TRUE, orderBy, page, pageSize);
		List<ProductRepresentation> productRepresentations = new ArrayList<ProductRepresentation>();
		for(Product product : products.getElements()){
			productRepresentations.add(new ProductRepresentation(product));
		}
		Page<ProductRepresentation> productPage = new ListPage<ProductRepresentation>(productRepresentations, products.getPageNumber(), products.getPageSize(), products.getTotalNumberOfElements());
		return productPage;
	}

	public ProductDetailRepresentation getProduct(Integer id) throws Exception {
		Product product = productManager.get(id);
		if(product == null) {
			throw new ResourceNotFoundException("No product found with id " + id);
		}
		return new ProductDetailRepresentation(product);
	}

	public List<ReviewRepresentation> getAllReviews(Integer productId) throws Exception{

		List<Review> reviews = reviewManager.find(productId, null, null, 0, 0).getElements();

		List<ReviewRepresentation> reviewRepresentations = new ArrayList<ReviewRepresentation>();
		for(Review review : reviews){
			reviewRepresentations.add(new ReviewRepresentation(review));
		}
		return reviewRepresentations;
	}

	public ReviewRepresentation createReview(Integer productId, ReviewRequest reviewRequest) throws Exception {

		Review review = new Review();
		review.setTitle(reviewRequest.getTitle());
		review.setComment(reviewRequest.getComment());
		review.setRating(reviewRequest.getRating());
		review.setCreatedAt(new Date());
		review.setCustomer(customerManager.get(reviewRequest.getCustomerId()));
		review.setProduct(productManager.get(productId));
		reviewManager.save(review);
		ReviewRepresentation reviewRep = new ReviewRepresentation(review);
		return reviewRep;
	}

	public void updateReview(Integer id, ReviewRequest reviewRequest) throws Exception {

		Review review = reviewManager.get(id);
		review.setTitle(reviewRequest.getTitle());
		review.setComment(reviewRequest.getComment());
		review.setRating(reviewRequest.getRating());
		reviewManager.save(review);
	}

	public ReviewRepresentation getReview(Integer id, Integer productId) throws Exception {
		Review review = reviewManager.get(id);
		if(review == null) {
			throw new ResourceNotFoundException("No review found with id " + id);
		} else if (!review.getProduct().getId().equals(productId)){
			throw new ResourceNotFoundException("No review found with id " + id + " for product with id " + productId);
		}
		return new ReviewRepresentation(review);
	}

	public void deleteReview(Integer id, Integer productId) throws Exception {
		Review review = reviewManager.get(id);
		if(review == null) {
			throw new ResourceNotFoundException("No review found with id " + id);
		} else if (!review.getProduct().getId().equals(productId)){
			throw new ResourceNotFoundException("No review found with id " + id + " for product with id " + productId);
		}
		reviewManager.delete(id);
	}


}
