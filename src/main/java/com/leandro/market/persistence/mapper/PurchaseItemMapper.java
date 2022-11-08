package com.leandro.market.persistence.mapper;

import com.leandro.market.domain.PurchaseItem;
import com.leandro.market.persistence.entity.ComprasProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")//esto se hace con el fin de
//poderle inyectar, ya que no es de spring, es de mapstruct
public interface PurchaseItemMapper {
    @Mappings({
            @Mapping(source = "id.idProducto", target = "productId"),
            @Mapping(source = "cantidad", target = "quantity"),
            @Mapping(source = "estado", target = "active")
           // @Mapping(source = "total", target = "total")
    })//cuando el source y el target se llaman iguales, no hace falta escribirlo
    PurchaseItem toPurchaseItem(ComprasProducto producto);

    @InheritInverseConfiguration
    @Mappings({//se deben incluir todos los atributos dentro del mapper asi sea para ignorarlos
            @Mapping(target = "compra", ignore = true),
            @Mapping(target = "producto", ignore = true),
            @Mapping(target = "id.idCompra", ignore = true)
    })
    ComprasProducto toComprasProducto(PurchaseItem item);
}

