package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.CustomerManager;
import com.comp344.ecommerce.business.LoginManager;
import com.comp344.ecommerce.business.PartnerManager;
import com.comp344.ecommerce.domain.Customer;
import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.domain.LoginRole;
import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.jwt.AuthorizationUtil;
import com.comp344.ecommerce.jwt.TokenAuthenticationService;
import com.comp344.ecommerce.jwt.UserAuthentication;
import com.comp344.ecommerce.jwt.UserService;
import com.comp344.ecommerce.service.representation.BaseRepresentation;
import com.comp344.ecommerce.service.representation.CustomerRepresentation;
import com.comp344.ecommerce.service.representation.LoginRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Byambatsog on 12/2/16.
 */
@Component
public class LoginActivity {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private AuthorizationUtil authorizationUtil;

    @Autowired
    private LoginManager loginManager;

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private PartnerManager partnerManager;

    public LoginRepresentation login( String username, String password, HttpServletResponse response) throws Exception {

        try{
            Authentication auth = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Username or password is incorrect. Please try again!");
        }

        User user = userService.loadUserByUsername(username);
        UserAuthentication authentication = new UserAuthentication(user);

        LoginRepresentation loginRepresentation = new LoginRepresentation(user.getUsername(), tokenAuthenticationService.addAuthentication(response, authentication));
        setLink(loginRepresentation, authentication);
        return loginRepresentation;
    }

    private void setLink(LoginRepresentation loginRepresentation, UserAuthentication authentication) {
        Login login = loginManager.findByEmail((String)authentication.getPrincipal());
        if(login.getRole().equals(LoginRole.ROLE_CUSTOMER)){
            Customer customer = customerManager.findByLogin(login.getId());
            loginRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/customers/" + customer.getId(),
                    "customer", HttpMethod.GET, "");
        }

        if(login.getRole().equals(LoginRole.ROLE_PARTNER)){
            Partner partner = partnerManager.findByLogin(login.getId());
            loginRepresentation.addLink(BaseRepresentation.BASE_URI + "/partnerservice/partners/" + partner.getId(),
                    "partner", HttpMethod.GET, "");
        }

    }
}
