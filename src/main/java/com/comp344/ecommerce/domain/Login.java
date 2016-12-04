package com.comp344.ecommerce.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Byambatsog on 9/27/16.
 */
@Entity
@Table(name="login")
public class Login implements Serializable{

    private static final long serialVersionUID = 6521176094999057607L;

    private Integer id;
    private String email;
    private String password;
    private Boolean active;
    private Boolean admin;
    private Date createdAt;
    private LoginRole role;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Column(name = "created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Enumerated(EnumType.STRING)
    public LoginRole getRole() {
        return role;
    }

    public void setRole(LoginRole role) {
        this.role = role;
    }
}
