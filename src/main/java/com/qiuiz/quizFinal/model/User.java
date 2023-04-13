package com.qiuiz.quizFinal.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "usersquiz")
public class User implements UserDetails {
    @Id
    @Column(name = "iduser")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iduser;
    @Column(name = "login")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "photo")
    private byte [] photo;
    @Column(name = "user_about")
    private String user_about;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Roles> rolesList = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, String email, Roles roles, byte [] photo, String user_about) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.rolesList.add(roles);
        this.photo = photo;
        this.user_about = user_about;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getUsename() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Roles> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Roles> rolesList) {
        this.rolesList = rolesList;
    }

    public String getUser_about() {
        if(user_about == null){
            return null;
        }
        return user_about;
    }

    public void setUser_about(String user_about) {
        this.user_about = user_about;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoBase64() {
        if(photo == null)
        {
            return  null;
        }

        return Base64.getEncoder().encodeToString(getPhoto());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Roles> roles = getRolesList();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Roles role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
