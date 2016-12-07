package com.comp344.ecommerce.service.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.comp344.ecommerce.business.CustomerManager;
import com.comp344.ecommerce.business.PartnerManager;
import com.comp344.ecommerce.business.ReviewManager;
import com.comp344.ecommerce.domain.Customer;
import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.domain.Review;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.jwt.AuthorizationUtil;
import com.comp344.ecommerce.service.representation.*;
import com.comp344.ecommerce.utils.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.comp344.ecommerce.business.ProductManager;
import com.comp344.ecommerce.domain.Product;
import com.comp344.ecommerce.utils.Page;

@Component
public class ProductActivity {
	
	@Autowired
    private ProductManager productManager;

	@Autowired
	private ReviewManager reviewManager;

	@Autowired
	private CustomerManager customerManager;

	@Autowired
	private AuthorizationUtil authorizationUtil;

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
			ProductRepresentation productRepresentation = new ProductRepresentation(product);
			setLink(productRepresentation, product);
			productRepresentations.add(productRepresentation);
		}

		String queryParam = "";
		if(searchQuery != null && !searchQuery.equals(""))
			queryParam += "q=" + searchQuery + "&";
		if(categoryId != null)
			queryParam += "categoryId=" + categoryId + "&";
		if(partnerId != null)
			queryParam += "partnerId=" + partnerId + "&";

		ListPage<ProductRepresentation> productPage = new ListPage<ProductRepresentation>(productRepresentations, products.getPageNumber(), products.getPageSize(), products.getTotalNumberOfElements());
		setLinks(productPage, queryParam);
		return productPage;
	}

	private void setLinks(ListPage<ProductRepresentation> productPage, String queryParam) {

		int previousPage = 0;
		int nextPage = 0;

		if(productPage.getPageNumber() > 1)
			previousPage = productPage.getPageNumber() - 1;

		if(productPage.getPageNumber() * productPage.getPageSize() < productPage.getTotalNumberOfElements())
			nextPage = productPage.getPageNumber() + 1;


		if(previousPage != 0){
			productPage.addLink(BaseRepresentation.BASE_URI + "/productservice/products?" + queryParam + "page=" + previousPage + "&pageSize=" + productPage.getPageSize(),
					"previous", HttpMethod.GET, "");
		}

		if(nextPage != 0){
			productPage.addLink(BaseRepresentation.BASE_URI + "/productservice/products?" + queryParam + "page=" + nextPage + "&pageSize=" + productPage.getPageSize(),
					"next", HttpMethod.GET, "");
		}
	}

	public ProductDetailRepresentation getProduct(Integer id) throws Exception {
		Product product = productManager.get(id);
		if(product == null) {
			throw new ResourceNotFoundException("No product found with id " + id);
		}
		ProductDetailRepresentation productDetailRepresentation = new ProductDetailRepresentation(product);
		setLinks(productDetailRepresentation, product);
		return productDetailRepresentation;
	}

	public List<ReviewRepresentation> getAllReviews(Integer productId) throws Exception{

		List<Review> reviews = reviewManager.find(productId, null, null, 0, 0).getElements();

		List<ReviewRepresentation> reviewRepresentations = new ArrayList<ReviewRepresentation>();
		for(Review review : reviews){
			ReviewRepresentation reviewRepresentation = new ReviewRepresentation(review);
			setLink(reviewRepresentation, review);
			reviewRepresentations.add(reviewRepresentation);
		}
		return reviewRepresentations;
	}

	public ReviewRepresentation createReview(Integer productId, ReviewRequest reviewRequest) throws Exception {

		Customer customer = authorizationUtil.getAuthenticatedCustomer();
		Review review = new Review();
		review.setTitle(reviewRequest.getTitle());
		review.setComment(reviewRequest.getComment());
		review.setRating(reviewRequest.getRating());
		review.setCreatedAt(new Date());
		review.setCustomer(customer);
		review.setProduct(productManager.get(productId));
		reviewManager.save(review);
		ReviewRepresentation reviewRep = new ReviewRepresentation(review);
		setLinks(reviewRep, review);
		return reviewRep;
	}

	public void updateReview(Integer id, ReviewRequest reviewRequest) throws Exception {

		Review review = reviewManager.get(id);
		authorizationUtil.authorize(review.getCustomer());
		review.setTitle(reviewRequest.getTitle());
		review.setComment(reviewRequest.getComment());
		review.setRating(reviewRequest.getRating());
		reviewManager.save(review);
	}

	public ReviewRepresentation getReview(Integer id) throws Exception {
		Review review = reviewManager.get(id);
		if(review == null) {
			throw new ResourceNotFoundException("No review found with id " + id);
		}
		authorizationUtil.authorize(review.getCustomer());
		ReviewRepresentation reviewRep = new ReviewRepresentation(review);
		setLinks(reviewRep, review);
		return reviewRep;
	}

	public void deleteReview(Integer id) throws Exception {
		Review review = reviewManager.get(id);
		if(review == null) {
			throw new ResourceNotFoundException("No review found with id " + id);
		}
		authorizationUtil.authorize(review.getCustomer());
		reviewManager.delete(id);
	}

	public ProductPartnerRepresentation getPartners(Integer productId) throws Exception {
		Product product = productManager.get(productId);
		if(product == null) {
			throw new ResourceNotFoundException("No product found with id " + productId);
		}
		ProductPartnerRepresentation partnerRep = new ProductPartnerRepresentation(product.getPartner());
		setLink(partnerRep, product.getPartner());
		return partnerRep;
	}

	private void setLinks(ReviewRepresentation reviewRepresentation, Review review) {
		reviewRepresentation.addLink(BaseRepresentation.BASE_URI + "/productservice/reviews/" + review.getId(),
				"self", HttpMethod.GET, "");
		reviewRepresentation.addLink(BaseRepresentation.BASE_URI + "/productservice/reviews/" + review.getId(),
				"update", HttpMethod.PUT, "application/json");
		reviewRepresentation.addLink(BaseRepresentation.BASE_URI + "/productservice/reviews/" + review.getId(),
				"delete", HttpMethod.DELETE, "");
	}

	private void setLink(ReviewRepresentation reviewRepresentation, Review review) {
		reviewRepresentation.addLink(BaseRepresentation.BASE_URI + "/productservice/reviews/" + review.getId(),
				"self", HttpMethod.GET, "");
	}

	private void setLink(ProductRepresentation productRepresentation, Product product) {
		productRepresentation.addLink(BaseRepresentation.BASE_URI + "/productservice/products/" + product.getId(),
				"self", HttpMethod.GET, "");
	}

	private void setLinks(ProductDetailRepresentation productDetailRepresentation, Product product) {
		productDetailRepresentation.addLink(BaseRepresentation.BASE_URI + "/productservice/products/" + product.getId(),
				"self", HttpMethod.GET, "");
		productDetailRepresentation.addLink(BaseRepresentation.BASE_URI + "/productservice/products/" + product.getId() + "/partners",
				"partners", HttpMethod.GET, "");
		productDetailRepresentation.addLink(BaseRepresentation.BASE_URI + "/productservice/products/" + product.getId() + "/reviews",
				"reviews", HttpMethod.GET, "");
		productDetailRepresentation.addLink(BaseRepresentation.BASE_URI + "/productservice/products/" + product.getId() + "/reviews",
				"reviews.post", HttpMethod.POST, "application/json");
		productDetailRepresentation.addLink(BaseRepresentation.BASE_URI + "/orderservice/orders",
				"orders.create", HttpMethod.POST, "application/json");
	}

	private void setLink(ProductPartnerRepresentation partnerRepresentation, Partner partner) {
		partnerRepresentation.addLink(BaseRepresentation.BASE_URI + "/productservice/products?partnerId=" + partner.getId(),
				"products", HttpMethod.GET, "");
	}
}
