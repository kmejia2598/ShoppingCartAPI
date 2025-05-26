package org.shoppingcart.dto.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_LEER("admin:leer"),
    ADMIN_CREAR("admin:crear"),
    ADMIN_MODIFICAR("admin:modificar"),
    ADMIN_BUSCAR("admin:buscar"),

    USER_LEER  ("user:leer"),
    USER_CREAR("user:crear"),
    USER_MODIFICAR("user:modificar"),
    USER_BUSCAR("user:buscar"),
    ;

    @Getter
    private final String permission;

}
