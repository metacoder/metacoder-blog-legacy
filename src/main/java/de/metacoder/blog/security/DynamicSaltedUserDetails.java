package de.metacoder.blog.security;

import de.metacoder.blog.persistence.entities.UserBO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class DynamicSaltedUserDetails implements UserDetails {


    private final String hashedAndSaltedPassword;
    private final String salt;
    private final String username;
    private final Collection<GrantedAuthority> grantedAuthorities;


    public DynamicSaltedUserDetails(UserBO userBO){
        hashedAndSaltedPassword = userBO.getPassword();
        salt = userBO.getSalt();
        username = userBO.getName();
        grantedAuthorities = new ArrayList<>();
        for(String role : userBO.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
    }

    public String getSalt(){
        return salt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return hashedAndSaltedPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // currently account expiration is not supported
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // currently account locking is not supported
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // currently credentials expiration is not supproted
    }

    @Override
    public boolean isEnabled() {
        return true; // currently disabling of accounts is not supported
    }
}
