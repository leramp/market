package com.leandro.market.web.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {
    private static final String KEY = "leramp";
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256, KEY ).compact();
    }//el builder nos va a permitir en un secuencia de métodos construir el jwt
    //lo primero que se le incluye es el dato mas importante del jwt que es el usuairo,
    //luego la fecha. Por último firmamos el método. El algoritmos que usamos para firmar el método
    //es el HS256.Para firmar necesitamos una clava que definimos como una constante. Para finalizar
    //usamos compact()
    //Para crear un jwt también debemos crear un controlador que reciba el usuario y la contraseña
    //y como respuesta envíe el jwt. Antes creamos un par de clases que nos ayuden a controlar esto. Lo hacemos dentro del paquete dto
}
