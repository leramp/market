package com.leandro.market.persistence.mapper;

import com.leandro.market.domain.Product;
import com.leandro.market.persistence.entity.Producto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",uses=CategoryMapper.class)
public interface ProductMapper {//este uses es porque como llevo la categoria
    //completa y category tiene su mapper propio, entonces de esta forma
    //sabe que cuando tenga que convertir categoria a category, tiene que usar
    //CategoryMapper
    @Mappings({
            @Mapping(source = "idProducto", target = "productId"),
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "precioVenta", target = "price"),
            @Mapping(source = "cantidadStock", target = "stock"),
            @Mapping(source = "estado", target = "active"),
            @Mapping(source = "categoria", target = "category"),
    })
    Product toProduct(Producto producto);
    List<Product> toProducts(List<Producto> productos);
    //no hace falta definir otro mapping porque es lo mismo que product
    //pero en plural, entonces hace el mismo tipo de converison

    @InheritConfiguration
    @Mapping(target="codigoBarras", ignore = true)
    Producto toProducto(Product product);
}
