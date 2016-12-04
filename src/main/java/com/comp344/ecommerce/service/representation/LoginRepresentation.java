package com.comp344.ecommerce.service.representation;

/**
 * Created by Byambatsog on 12/2/16.
 */
public class LoginRepresentation extends BaseRepresentation {

    private String email;
    private String token;

    public LoginRepresentation(){}

    public LoginRepresentation(String email, String token){
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
