package com.leandro.market.persistence.mapper;

import com.leandro.market.domain.Purchase;
import com.leandro.market.persistence.entity.Compra;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PurchaseItemMapper.class})
public interface PurchaseMapper {

    @Mappings({
            @Mapping(source = "idCompra", target = "purchaseId"),
            @Mapping(source = "idCliente", target = "clientId"),
            @Mapping(source = "fecha", target = "date"),
            @Mapping(source = "medioPago", target = "paymentMethod"),
            @Mapping(source = "comentario", target = "comment"),
            @Mapping(source = "estado", target = "state"),
            @Mapping(source = "productos", target = "items")
    })//este mapping en particular (el productos a items) es el que usa
    //PurchaseItemMapper para luego convertir los productos uno a uno
    Purchase toPurchase(Compra compra);
    //como ya definimos un mapper de compra a purschase
    //no hace falta definir uno para este en plural, ya que
    //adopta la configuración que se hizo para el singular
    List<Purchase> toPurchases(List<Compra> compras);

    @InheritInverseConfiguration
    @Mapping(target = "cliente", ignore = true)
     Compra toCompra(Purchase purchase);
}
