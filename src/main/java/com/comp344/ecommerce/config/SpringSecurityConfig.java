package com.comp344.ecommerce.config;

import com.comp344.ecommerce.exception.AccessDeniedHandlerImpl;
import com.comp344.ecommerce.exception.RestAuthenticationEntryPoint;
import com.comp344.ecommerce.jwt.StatelessAuthenticationFilter;
import com.comp344.ecommerce.jwt.TokenAuthenticationService;
import com.comp344.ecommerce.jwt.UserService;
import com.comp344.ecommerce.utils.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Byambatsog on 11/27/16.
 */
@Configuration
@EnableWebSecurity
@Order(2)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final TokenAuthenticationService tokenAuthenticationService;


    public SpringSecurityConfig() {
        super(true);
        this.userService = new UserService();
        tokenAuthenticationService = new TokenAuthenticationService("tooManySecrets", userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint()).and()
                .anonymous().and()
                .servletApi().and()
                .headers().cacheControl().and()
                .authorizeRequests()

                // Allow anonymous resource requests
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("**/*.html").permitAll()
                .antMatchers("**/*.css").permitAll()
                .antMatchers("**/*.js").permitAll()
                .antMatchers("/categoryservice/**").permitAll()

                .antMatchers(HttpMethod.POST, "/productservice/products/*/reviews").hasRole("CUSTOMER")
                .antMatchers("/productservice/reviews/*").hasRole("CUSTOMER")
                .antMatchers("/productservice/**").permitAll()

                .antMatchers(HttpMethod.POST, "/customerservice/customers").permitAll()
                .antMatchers(HttpMethod.GET, "/customerservice/customers").hasRole("ADMIN")
                .antMatchers("/customerservice/**").hasRole("CUSTOMER")

                .antMatchers(HttpMethod.POST, "/partnerservice/partners").permitAll()
                .antMatchers(HttpMethod.GET, "/partnerservice/partners").hasRole("ADMIN")
                .antMatchers("/partnerservice/**").hasRole("PARTNER")

                // Allow anonymous logins
                .antMatchers("/login").permitAll()

                // All other request need to be authenticated
                .anyRequest().authenticated().and()

                // Custom Token based authentication based on the header previously given to the client
                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new PasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserService userDetailsService() {
        return userService;
    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return tokenAuthenticationService;
    }

    @Bean
    public RestAuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandlerImpl accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }


}
