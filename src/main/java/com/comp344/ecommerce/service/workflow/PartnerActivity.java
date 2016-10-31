package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.LoginManager;
import com.comp344.ecommerce.business.PartnerManager;
import com.comp344.ecommerce.business.ProductManager;
import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.domain.PartnerType;
import com.comp344.ecommerce.domain.Product;
import com.comp344.ecommerce.exception.LoginRegistrationException;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.service.representation.*;
import com.comp344.ecommerce.utils.ListPage;
import com.comp344.ecommerce.utils.Page;
import com.comp344.ecommerce.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Byambatsog on 10/29/16.
 */
@Component
public class PartnerActivity {

    @Autowired
    private PartnerManager partnerManager;

    @Autowired
    private ProductManager productManager;

    @Autowired
    private LoginManager loginManager;

    public PartnerRepresentation getPartner(Integer id) throws Exception {
        Partner partner = partnerManager.get(id);
        if(partner == null) {
            throw new ResourceNotFoundException("No partner found with id " + id);
        }
        return new PartnerRepresentation(partner);
    }

    public Page<PartnerRepresentation> findPartners(String searchQuery, PartnerType type, String orderBy, int page, int pageSize) throws Exception{

        if(orderBy != null && !orderBy.equals("")){
            if (orderBy.equals("created")){
                orderBy = "order by created_at desc";
            } else if (orderBy.equals("name")){
                orderBy = "order by company_name asc";
            }
        }

        Page<Partner> list = partnerManager.find(searchQuery, type, orderBy, page, pageSize);
        List<PartnerRepresentation> partnerRepresentations = new ArrayList<PartnerRepresentation>();
        for(Partner partner : list.getElements()){
            partnerRepresentations.add(new PartnerRepresentation(partner));
        }
        Page<PartnerRepresentation> partnerPage = new ListPage<PartnerRepresentation>(partnerRepresentations, list.getPageNumber(), list.getPageSize(), list.getTotalNumberOfElements());
        return partnerPage;
    }

    public PartnerRepresentation createPartner(PartnerCreateRequest partnerCreateRequest) throws Exception {

        Login exists = loginManager.findByEmail(partnerCreateRequest.getEmail());
        if(exists != null)
            throw new LoginRegistrationException("Following email has already registered: " + partnerCreateRequest.getEmail());

        PasswordEncoder passwordEncoder = new PasswordEncoder();

        Login login = new Login();
        login.setEmail(partnerCreateRequest.getEmail());
        login.setPassword(passwordEncoder.encode(partnerCreateRequest.getPassword()));
        login.setActive(Boolean.TRUE);
        login.setAdmin(Boolean.FALSE);
        login.setCreatedAt(new Date());

        Partner partner = new Partner();
        partner.setLogin(login);
        partner.setFirstName(partnerCreateRequest.getFirstName());
        partner.setLastName(partnerCreateRequest.getLastName());
        if(partnerCreateRequest.getCompanyName() != null && !partnerCreateRequest.getCompanyName().equals("")){
            partner.setCompanyName(partnerCreateRequest.getCompanyName());
            partner.setType(PartnerType.COMPANY);
        } else {
            partner.setType(PartnerType.PERSONAL);
        }
        partner.setEmail(partnerCreateRequest.getEmail());
        partner.setCreatedAt(new Date());
        partnerManager.create(partner);

        return new PartnerRepresentation(partner);
    }

    public void updatePartner(Integer id, PartnerRequest partnerRequest) throws Exception {

        Partner partner = partnerManager.get(id);
        partner.setCompanyName(partnerRequest.getCompanyName());
        partner.setFirstName(partnerRequest.getFirstName());
        partner.setLastName(partnerRequest.getLastName());
        partner.setType(partnerRequest.getType());
        partner.setPhone(partnerRequest.getPhone());
        partner.setEmail(partnerRequest.getEmail());
        partner.setStreet(partnerRequest.getStreet());
        partner.setCity(partnerRequest.getCity());
        partner.setState(partnerRequest.getState());
        partner.setZipCode(partnerRequest.getZipCode());
        partner.setCountry(partnerRequest.getCountry());
        partnerManager.update(partner);
    }

    public void deletePartner(Integer id) throws Exception {
        partnerManager.delete(id);
    }

    public Page<PartnerProductRepresentation> findProducts(String searchQuery, Integer categoryId, Integer partnerId, Boolean status,
                                                           String orderBy, int page, int pageSize) throws Exception{

        if(orderBy != null && !orderBy.equals("")){
            if (orderBy.equals("created")){
                orderBy = "order by created_at desc";
            } else if (orderBy.equals("name")){
                orderBy = "order by name asc";
            }
        }

        Page<Product> products = productManager.find(searchQuery, categoryId, partnerId, status, orderBy, page, pageSize);

        List<PartnerProductRepresentation> productRepresentations = new ArrayList<PartnerProductRepresentation>();
        for(Product product : products.getElements()){
            productRepresentations.add(new PartnerProductRepresentation(product));
        }
        Page<PartnerProductRepresentation> productPage = new ListPage<PartnerProductRepresentation>(productRepresentations, products.getPageNumber(), products.getPageSize(), products.getTotalNumberOfElements());
        return productPage;
    }

    public PartnerProductRepresentation createProduct(Integer partnerId, ProductRequest productRequest) throws Exception {

        Product product = new Product();
        product.setId(productRequest.getId());
        product.setName(productRequest.getName());
        product.setPicture(productRequest.getPicture());
        product.setBrandName(productRequest.getBrandName());
        product.setDescription(productRequest.getDescription());
        product.setStatus(productRequest.getStatus());
        product.setQuantityInStock(productRequest.getQuantityInStock());
        product.setUnitPrice(productRequest.getUnitPrice());
        product.setWeight(productRequest.getWeight());
        product.setCreatedAt(new Date());
        product.setCategory(productManager.getCategory(productRequest.getCategoryId()));
        product.setPartner(partnerManager.get(partnerId));
        productManager.save(product);
        PartnerProductRepresentation productRep = new PartnerProductRepresentation(product);
        return productRep;
    }

    public void updateProduct(Integer id, ProductRequest productRequest) throws Exception {


        Product product = productManager.get(id);
        product.setName(productRequest.getName());
        product.setPicture(productRequest.getPicture());
        product.setBrandName(productRequest.getBrandName());
        product.setDescription(productRequest.getDescription());
        product.setStatus(productRequest.getStatus());
        product.setQuantityInStock(productRequest.getQuantityInStock());
        product.setUnitPrice(productRequest.getUnitPrice());
        product.setWeight(productRequest.getWeight());
        product.setCategory(productManager.getCategory(productRequest.getCategoryId()));
        productManager.save(product);
    }
}
