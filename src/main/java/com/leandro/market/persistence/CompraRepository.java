package com.leandro.market.persistence;

import com.leandro.market.domain.Purchase;
import com.leandro.market.domain.repository.PurchaseRepository;
import com.leandro.market.persistence.crud.CompraCrudRepository;
import com.leandro.market.persistence.entity.Compra;
import com.leandro.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {
    @Autowired
    private CompraCrudRepository compraCrudRepository;
    @Autowired//se debe inyectar el mapper porque este repositoria
    //debe hablar en términos del dominio
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId)
                .map(compras -> mapper.toPurchases(compras));
    }//el .map nos sirve para operar con lo que sea que venga dentro
    //del optional, si no hay nada, no se ejecuta

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra(purchase);
        //esta información se debe guardar en cascada, para eso debemos estar
        //seguros que compra conoce los productos y los productos conocen a que
        //compra pertenecen. Para eso le decimos:
        compra.getProductos().forEach(producto -> producto.setCompra(compra));
        //para que eso ocurra, hay que ir al entity Comra y decirle que producto
        //se va a guardar en cascada
        return mapper.toPurchase(compraCrudRepository.save(compra));
    }
}
