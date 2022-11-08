package com.leandro.market.domain.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MarketUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("Leandro", "{noop}root", new ArrayList<>());//el array es el perfil o los tipos de roles del usuario
    }//la palabra noop entre llaves se usa porque el password no ha sido encodedo
}//este usuario es un demo, lo que debería hacerse es ir a la bbdd para verificar el correcto inicio de sesión antes de usar nuestros servicios
