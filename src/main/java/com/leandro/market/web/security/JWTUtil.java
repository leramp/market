package com.leandro.market.web.security;

import io.jsonwebtoken.Claims;
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

    public boolean vadlidateToken(String token, UserDetails userDetails){
        //primero validamos que el usuario de la peticion sea el mismo que el del token
        return userDetails.getUsername().equals(extractUsername(token))
                //y luego preguntamos que no haya expirado el token
                && !isTokenExpired(token);
    }
    //para poder verificar un jwt  lo primero es verifcar q esté creado para el usuario que de verdad esta haciendo la peticion
    //y lo segundo que no haya vencido. para eso creamos un método auxiliar que retorne los claims. Estos son objetos dentro del jwt
    private Claims getClaims(String token){
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }//lo que hago acá es al parser le añado la llave de la firma, y cuando él verifique
    //que es firma sea correcta lo que voy a hacer es que me va a obtener los claims o el cuerpo
    //de mi jws separado por cada uno de los objetos

    //entonces vamos a obtener un método que se encargue de extraer el usuario
    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }
    //voy a tener otro método que verifique si el token ya expiró
    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
        //si está antes de la fecha actual va retornar true y si esta después false

    }
    //para continuar creamos un filtro que atrape todas las peticiones que recibe la aplicacion
    //y que verifique si el jwt es correcto. Para eso creamos otro paquete filter con una clase JWTFilterRequest
}
