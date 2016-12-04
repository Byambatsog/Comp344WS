package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.CreditCard;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class CreditCardRepresentation extends BaseRepresentation {

    private Integer id;
    private String cardNumber;
    private String cardType;
    private Integer expireMonth;
    private Integer expireYear;
    private String nameOnCard;

    public CreditCardRepresentation(){}

    public CreditCardRepresentation(CreditCard card){
        this.id = card.getId();
        this.cardNumber = "..." + card.getCardNumber().substring(card.getCardNumber().length() - 4);
        this.cardType = card.getCardType();
        this.expireMonth = card.getExpireMonth();
        this.expireYear = card.getExpireYear();
        this.nameOnCard = card.getNameOnCard();
    }

    public Integer getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public Integer getExpireMonth() {
        return expireMonth;
    }

    public Integer getExpireYear() {
        return expireYear;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

}
