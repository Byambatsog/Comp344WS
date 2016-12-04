package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.LoginManager;
import com.comp344.ecommerce.business.OrderManager;
import com.comp344.ecommerce.business.PartnerManager;
import com.comp344.ecommerce.business.ProductManager;
import com.comp344.ecommerce.domain.*;
import com.comp344.ecommerce.exception.LoginRegistrationException;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.jwt.AuthorizationUtil;
import com.comp344.ecommerce.service.representation.*;
import com.comp344.ecommerce.utils.ListPage;
import com.comp344.ecommerce.utils.Page;
import com.comp344.ecommerce.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private AuthorizationUtil authorizationUtil;

    public PartnerRepresentation getPartner(Integer id) throws Exception {
        Partner partner = partnerManager.get(id);
        authorizationUtil.authorize(partner);
        if(partner == null) {
            throw new ResourceNotFoundException("No partner found with id " + id);
        }
        PartnerRepresentation partnerRepresentation = new PartnerRepresentation(partner);
        setLinks(partnerRepresentation, partner);
        return partnerRepresentation;
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
            PartnerRepresentation partnerRepresentation = new PartnerRepresentation(partner);
            setLink(partnerRepresentation, partner);
            partnerRepresentations.add(partnerRepresentation);
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
        login.setRole(LoginRole.ROLE_PARTNER);
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

        PartnerRepresentation partnerRepresentation = new PartnerRepresentation(partner);
        setLinks(partnerRepresentation, partner);
        return partnerRepresentation;
    }

    public void updatePartner(Integer id, PartnerRequest partnerRequest) throws Exception {

        Partner partner = partnerManager.get(id);
        authorizationUtil.authorize(partner);

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
        Partner partner = partnerManager.get(id);
        authorizationUtil.authorize(partner);
        if(partner == null) {
            throw new ResourceNotFoundException("No partner found with id " + id);
        }
        partnerManager.delete(id);
    }

    public Page<PartnerProductRepresentation> findProducts(String searchQuery, Integer categoryId, Integer partnerId, Boolean status,
                                                           String orderBy, int page, int pageSize) throws Exception{

        Partner partner = partnerManager.get(partnerId);
        authorizationUtil.authorize(partner);

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
            PartnerProductRepresentation productRepresentation = new PartnerProductRepresentation(product);
            setLinks(productRepresentation, product);
            productRepresentations.add(productRepresentation);
        }
        Page<PartnerProductRepresentation> productPage = new ListPage<PartnerProductRepresentation>(productRepresentations, products.getPageNumber(), products.getPageSize(), products.getTotalNumberOfElements());
        return productPage;
    }

    public PartnerProductRepresentation createProduct(Integer partnerId, ProductRequest productRequest) throws Exception {

        Partner partner = partnerManager.get(partnerId);
        authorizationUtil.authorize(partner);
        Product product = new Product();
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
        setLinks(productRep, product);
        return productRep;
    }

    public void updateProduct(Integer id, ProductRequest productRequest) throws Exception {

        Product product = productManager.get(id);
        authorizationUtil.authorize(product.getPartner());

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

    public PartnerProductRepresentation getProduct(Integer id) throws Exception {
        Product product = productManager.get(id);
        if(product == null) {
            throw new ResourceNotFoundException("No product found with id " + id);
        }
        authorizationUtil.authorize(product.getPartner());
        PartnerProductRepresentation productRep = new PartnerProductRepresentation(product);
        setLinks(productRep, product);
        return productRep;
    }

    public void deleteProduct(Integer id) throws Exception {
        Product product = productManager.get(id);
        if(product == null) {
            throw new ResourceNotFoundException("No product found with id " + id);
        }
        authorizationUtil.authorize(product.getPartner());
        productManager.delete(id);
    }

    private void setLink(PartnerRepresentation partnerRepresentation, Partner partner) {
        partnerRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/partners/" + partner.getId(),
                "self", HttpMethod.GET, "");
    }

    private void setLinks(PartnerRepresentation partnerRepresentation, Partner partner) {
        partnerRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/partners/" + partner.getId(),
                "self", HttpMethod.GET, "");
        partnerRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/partners/" + partner.getId(),
                "update", HttpMethod.PUT, "application/json");
        partnerRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/partners/" + partner.getId(),
                "delete", HttpMethod.DELETE, "");
        partnerRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/partners/" + partner.getId() + "/products",
                "products", HttpMethod.GET, "");
        partnerRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/partners/" + partner.getId() + "/products",
                "products.create", HttpMethod.POST, "application/json");
        partnerRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/partners/" + partner.getId() + "/orders",
                "orders", HttpMethod.GET, "");
    }

    private void setLinks(PartnerProductRepresentation partnerProductRepresentation, Product product) {
        partnerProductRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/products/" + product.getId(),
                "self", HttpMethod.GET, "");
        partnerProductRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/products/" + product.getId(),
                "update", HttpMethod.PUT, "application/json");
        partnerProductRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/products/" + product.getId(),
                "delete", HttpMethod.DELETE, "");
    }


    public List<OrderRepresentation> getPartnerOrders(Integer partnerId, OrderProductStatus status) throws Exception {

        Partner partner = partnerManager.get(partnerId);
        authorizationUtil.authorize(partner);

        List<Order> orders = orderManager.findByPartner(partnerId, status, null, 0, 0).getElements();
        List<OrderRepresentation> orderRepresentations = new ArrayList<OrderRepresentation>();
        for(Order order : orders){
            Double totalPrice = 0d;
            List<OrderProduct> orderProducts = orderManager.findProduct(order.getId(), null, partnerId, status, null);
            for(OrderProduct orderProduct : orderProducts){
                totalPrice = totalPrice + orderProduct.getQuantity() * orderProduct.getUnitPrice();
            }
            order.setTotalPrice(totalPrice);
            OrderRepresentation orderRepresentation = new OrderRepresentation(order);
            setLink(orderRepresentation, order);
            orderRepresentations.add(orderRepresentation);
        }
        return orderRepresentations;
    }

    public OrderRepresentation getPartnerOrder(Integer orderId) throws Exception {

        Partner partner = authorizationUtil.getAuthenticatedPartner();

        List<Order> orders = orderManager.findByPartner(partner.getId(), null, null, 0, 0).getElements();
        for(Order order : orders){
            if(order.getId().equals(orderId)){
                Double totalPrice = 0d;
                List<OrderProduct> orderProducts = orderManager.findProduct(order.getId(), null, partner.getId(), null, null);
                for(OrderProduct orderProduct : orderProducts){
                    totalPrice = totalPrice + orderProduct.getQuantity() * orderProduct.getUnitPrice();
                }
                order.setTotalPrice(totalPrice);
                order.setProducts(orderProducts);

                OrderRepresentation orderRepresentation = new OrderRepresentation(order);
                for(OrderProduct orderProduct : order.getProducts()){
                    OrderProductRepresentation productRepresentation = new OrderProductRepresentation(orderProduct);
                    setLinks(productRepresentation, orderProduct);
                    orderRepresentation.getProducts().add(productRepresentation);
                }
                setLink(orderRepresentation, order);
                return orderRepresentation;
            }
        }
        throw new ResourceNotFoundException("No order found with id " + orderId);
    }

    private void setLink(OrderRepresentation orderRepresentation, Order order) {
        orderRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/orders/" + order.getId(),
                "self", HttpMethod.GET, "");
    }

    public void fulfillOrderProduct(Integer orderId, Integer productId, String trackingNumber) throws Exception {

        Partner partner = authorizationUtil.getAuthenticatedPartner();

        OrderProduct orderProduct = null;
        List<OrderProduct> orderProducts = orderManager.findProduct(orderId, productId, partner.getId(), OrderProductStatus.ORDERED, null);
        if(orderProducts != null && orderProducts.size() > 0)
            orderProduct = orderProducts.get(0);

        if(orderProduct == null)
            throw new ResourceNotFoundException("No order product is found with productId " + productId);

        orderProduct.setStatus(OrderProductStatus.FULFILLED);
        orderProduct.setTrackingNumber(trackingNumber);
        orderManager.saveProduct(orderProduct);

        orderProducts = orderManager.findProduct(orderId, null, null, null, null);
        boolean allfulfilled = true;
        for(OrderProduct ordProduct : orderProducts){
            if(!ordProduct.getStatus().equals(OrderProductStatus.FULFILLED))
                allfulfilled = false;
        }

        if(allfulfilled){
            Order order = orderManager.get(orderId);
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setCreatedAt(new Date());
            orderStatus.setStatus(OrderStatusType.FULFILLED);
            orderStatus.setOrder(order);
            orderManager.saveStatus(orderStatus);
            order.setLastStatus(OrderStatusType.FULFILLED);
            orderManager.save(order);
        }
    }

    public void shipOrderProduct(Integer orderId, Integer productId) throws Exception {

        Partner partner = authorizationUtil.getAuthenticatedPartner();

        OrderProduct orderProduct = null;
        List<OrderProduct> orderProducts = orderManager.findProduct(orderId, productId, partner.getId(), OrderProductStatus.FULFILLED, null);
        if(orderProducts != null && orderProducts.size() > 0)
            orderProduct = orderProducts.get(0);

        if(orderProduct == null)
            throw new ResourceNotFoundException("No order product is found with productId " + productId);


        orderProduct.setStatus(OrderProductStatus.SHIPPED);
        orderManager.saveProduct(orderProduct);

        orderProducts = orderManager.findProduct(orderId, null, null, null, null);
        boolean allshipped = true;
        for(OrderProduct ordProduct : orderProducts){
            if(!ordProduct.getStatus().equals(OrderProductStatus.SHIPPED))
                allshipped = false;
        }

        if(allshipped){
            Order order = orderManager.get(orderId);
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setCreatedAt(new Date());
            orderStatus.setStatus(OrderStatusType.SHIPPED);
            orderStatus.setOrder(order);
            orderManager.saveStatus(orderStatus);
            order.setLastStatus(OrderStatusType.SHIPPED);
            orderManager.save(order);
        }
    }

    public void setOrderProductStatusDelivered(Integer orderId, Integer productId) throws Exception {

        Partner partner = authorizationUtil.getAuthenticatedPartner();

        OrderProduct orderProduct = null;
        List<OrderProduct> orderProducts = orderManager.findProduct(orderId, productId, partner.getId(), OrderProductStatus.SHIPPED, null);
        if(orderProducts != null && orderProducts.size() > 0)
            orderProduct = orderProducts.get(0);

        if(orderProduct == null)
            throw new ResourceNotFoundException("No order product is found with productId " + productId);

        orderProduct.setStatus(OrderProductStatus.DELIVERED);
        orderManager.saveProduct(orderProduct);

        orderProducts = orderManager.findProduct(orderId, null, null, null, null);
        boolean alldelivered = true;
        for(OrderProduct ordProduct : orderProducts){
            if(!ordProduct.getStatus().equals(OrderProductStatus.DELIVERED))
                alldelivered = false;
        }

        if(alldelivered){
            Order order = orderManager.get(orderId);
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setCreatedAt(new Date());
            orderStatus.setStatus(OrderStatusType.DELIVERED);
            orderStatus.setOrder(order);
            orderManager.saveStatus(orderStatus);
            order.setLastStatus(OrderStatusType.DELIVERED);
            orderManager.save(order);
        }
    }

    private void setLinks(OrderProductRepresentation orderProductRepresentation, OrderProduct orderProduct) {

        if(orderProduct.getStatus().equals(OrderProductStatus.ORDERED))
            orderProductRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/orders/" + orderProduct.getOrder().getId()
                    + "/products/" + orderProduct.getProduct().getId(), "fulfill", HttpMethod.POST, "");
        if(orderProduct.getStatus().equals(OrderProductStatus.FULFILLED))
            orderProductRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/orders/" + orderProduct.getOrder().getId()
                    + "/products/" + orderProduct.getProduct().getId() + "?status=" + OrderProductStatus.SHIPPED
                    , "ship", HttpMethod.PUT, "");
        if(orderProduct.getStatus().equals(OrderProductStatus.SHIPPED))
            orderProductRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/orders/" + orderProduct.getOrder().getId()
                            + "/products/" + orderProduct.getProduct().getId() + "?status=" + OrderProductStatus.DELIVERED
                    , "deliver", HttpMethod.PUT, "");

    }
}
