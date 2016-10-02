package com.comp344.ecommerce.utils;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Byambatsog on 10/2/16.
 */
public class PasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder{

    @Value("${security.passwordSand}")
    private String sand;

    public String encode(CharSequence charSequence) {
        return PasswordGenerator.encryptPassword(charSequence.toString(),sand);
    }

    public boolean matches(CharSequence charSequence, String s) {
        return PasswordGenerator.encryptPassword(charSequence.toString(),sand).equals(s);
    }
}
