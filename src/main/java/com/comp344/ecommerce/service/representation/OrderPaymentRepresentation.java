package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.OrderPayment;

import java.text.SimpleDateFormat;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class OrderPaymentRepresentation {

    private String cardNumber;
    private Double amount;
    private String paidAt;

    public OrderPaymentRepresentation(){}

    public OrderPaymentRepresentation(OrderPayment orderPayment){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.cardNumber = "..." + orderPayment.getCard().getCardNumber().substring(orderPayment.getCard().getCardNumber().length() - 4);
        this.amount = orderPayment.getAmount();
        this.paidAt = dateFormat.format(orderPayment.getPaidAt());
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPaidAt() {
        return paidAt;
    }
}
