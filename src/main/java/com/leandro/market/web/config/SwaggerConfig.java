package com.leandro.market.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //aca le decimos que solo los componentes dentro de controller estén expuestos a la doc
                //.apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.leandro.market.web.controller"))
                .build();
    }
}

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.util.Collections;

///**
// * Configuración Swagger para la generación de documentación de la API REST
// *
// * http://localhost:8081/swagger-ui/
// */
//@Configuration
//public class SwaggerConfig {
//
//    @Bean //spring detecta un bean y lo crea y lo inyecta en las librerías
//    //que se descargaron con la dependencia de springfox y hará su magia,
//    //crará una web con la documentación de forma dinámica
//    public Docket api(){//vamos a ver una opción sencilla de crear este objeto
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiDetails())//enlace a doc, contacto y cosas asi
//                .select()//le especificamos que enganche las rutas paragenerar documentación para toas esas rutas
//                .apis(RequestHandlerSelectors.any())//para eso, aca se usa el Req....
//                .paths(PathSelectors.any())//y aca se agrega esto. To do esto es para enganchar las rutas
//                .build();//y finalmente construir las llamadas
//
//    }
//    private ApiInfo apiDetails(){
//        return new ApiInfo("Disney con API REST", "Api rest docs",
//                "1.0",
//                "http://www.google.com",
//                new Contact("Leandro",
//                        "http://www.google.com",
//                        "leandroramp@gmail.com"),
//                "MIT",
//                "http://www.google.com",
//                Collections.emptyList());
//    }



