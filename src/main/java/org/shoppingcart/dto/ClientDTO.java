package org.shoppingcart.dto;

import lombok.Data;
import org.shoppingcart.dto.login.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class ClientDTO implements UserDetails {
    private Integer id;
    private String email;
    private String username;
    private String password;
    private String phone;
    private Role role;
    private NameDTO name;
    private AddressDTO address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}