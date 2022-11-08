package com.leandro.market.web.filter;

import com.leandro.market.domain.service.MarketUserDetailsService;
import com.leandro.market.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilterRequest extends OncePerRequestFilter { //hereda de esta clase para que este filtro se ejecute cada vez que exista una petición
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private MarketUserDetailsService marketUserDetailsService;

    @Override//con esto método verifico si lo que viene en el encabezado de la peticion es un token y si es correcto
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authentication");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
            String jwt =  authorizationHeader.substring(7);
            //además voy a verificar el usuario de ese jwt
            //para eso tengo que inyectar el JWTUtil
            String username =  jwtUtil.extractUsername(jwt);
            //otra velidación: si es dif a nulo que aún no haya ingresado a nuestra aplicación y que no esté debidamente logueado
            if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
                //el servicio marketUserServiceDetails es el que va a verificar el usuario que haya llegado en el jwt, si existe o no en nuestor sistema de autenticacion
                UserDetails userDetails = marketUserDetailsService.loadUserByUsername(username);
            //luego preguntamos si el jwt es correcto
                if(jwtUtil.vadlidateToken(jwt, userDetails)){
                    //si esto pasa la validación lo que hacemos es:
                    UsernamePasswordAuthenticationToken authToken  = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //null porque no le mandamos ninguna credencial y
                    //getAuthorities para que se envíen ahí todos los roles que tiene el usuario, aunque en este caso tenemos roles vacíos
                    //ahora le ponemos a este authToken los detalles de la conexión que está recibiendo
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));//con esto último lo que hacemos es
                    //poder evaluar qué navegador estaba usando, qué hora se conectó, que sistema operativo tenía, etc.
                    //por último le asignamos la autenticacion para que la próxima vez no tenga que  pasar por toda la validación de este filtro gracias a la validación SecurityContextHolder.getContext().getAuthentication()==null
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        //por último le decimos que el filtro sea evaluado con FilterChain....
        filterChain.doFilter(request, response);
        //para terminar solo nos queda en el archivo SecurityConfig decirle que ese filtro será
        //el encargado de recibir todas las peticiones y procesarlas
    }
}
