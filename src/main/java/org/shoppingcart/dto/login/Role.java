package org.shoppingcart.dto.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.shoppingcart.dto.login.Permission.*;

@RequiredArgsConstructor
public enum Role {

    ADMIN(Set.of(
            ADMIN_LEER,
            ADMIN_CREAR,
            ADMIN_MODIFICAR,
            ADMIN_BUSCAR
    )),
    USER(Set.of(
            USER_LEER,
            USER_CREAR,
            USER_MODIFICAR,
            USER_BUSCAR
    )),
    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities =  getPermissions()
                .stream()
                .map( permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}