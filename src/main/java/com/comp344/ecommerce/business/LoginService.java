package com.comp344.ecommerce.business;

import com.comp344.ecommerce.dao.HibernateLoginRepository;
import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.utils.Page;
import com.mysql.jdbc.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Byambatsog on 10/2/16.
 */
@Service("loginService")
public class LoginService {

    @Autowired
    private HibernateLoginRepository loginRepository;

    public void save(Login login) throws Exception {
        loginRepository.save(login);
    }

    public Login get(Integer id) throws Exception {
        return loginRepository.get(id);
    }

    public Login findByUserName(String username){
        return loginRepository.findByUserName(username);
    }

    public Login findByEmail(String email){
        return loginRepository.findByEmail(email);
    }

    public Page<Login> find(String email, String username, Boolean active, Boolean admin, String orderBy, int page, int size){
        return loginRepository.find(email, username, active, admin, orderBy, page, size);
    }
}
