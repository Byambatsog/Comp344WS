package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.OrderStatus;
import com.comp344.ecommerce.domain.OrderStatusType;

import java.text.SimpleDateFormat;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class OrderStatusRepresentation {

    private OrderStatusType status;
    private String created;

    public OrderStatusRepresentation(){}

    public OrderStatusRepresentation(OrderStatus orderStatus){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.status = orderStatus.getStatus();
        this.created = dateFormat.format(orderStatus.getCreatedAt());
    }

    public OrderStatusType getStatus() {
        return status;
    }

    public String getCreated() {
        return created;
    }
}
