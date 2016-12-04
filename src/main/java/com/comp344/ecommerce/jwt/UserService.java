package com.comp344.ecommerce.jwt;

import com.comp344.ecommerce.business.LoginManager;
import com.comp344.ecommerce.domain.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Byambatsog on 11/27/16.
 */
public class UserService implements UserDetailsService {

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Autowired
    private LoginManager loginManager;

    @Override
    public final User loadUserByUsername(String username) throws UsernameNotFoundException {

        final Login login = loginManager.findByEmail(username);
        User user = null;

        if(login != null){
            Set<GrantedAuthority> authorities=new HashSet<GrantedAuthority>();
            if (login.getAdmin()){
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            authorities.add(new SimpleGrantedAuthority(login.getRole().name()));
            user = new User(login.getEmail(), login.getPassword(), login.getActive(), true, true, true, authorities);
        }
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        detailsChecker.check(user);
        return user;
    }
}
