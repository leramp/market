package com.leandro.market.web.security;


import com.leandro.market.domain.service.MarketUserDetailsService;
import com.leandro.market.web.filter.JwtFilterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MarketUserDetailsService marketUserDetailsService;

    @Autowired
    private JwtFilterRequest jwtFilterRequest;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(marketUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//aca se desabilitan las peticiones cruzadas
                .authorizeRequests().antMatchers("/**/authenticate")
        //esto dice que todas las peticiones, sin importar lo que haya antes,
        //estos dos asteriscos sirven como comodin, todas las peticiones que terminen en authenticate las permita
                .permitAll()
                .anyRequest().authenticated().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);//el segundo parámetro es para decirle qué tipo de filtro es, y es uno de usuario y contraseña
    }
    //como en el controller usamos el authenticationManager, también debemos incluirlo acá
    //para eso hacemos un override


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();//lo dejamos así para que Spring siga controlando la gestión de autenticación,
        // pero le agregamos el @Bean para que no simplemente lo utilice, sino que sepa que explícitamente lo estamos eligiendo a él como gestor de la aplicación
    }
}
