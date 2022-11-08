package com.leandro.market.domain.repository;

import java.util.List;
import java.util.Optional;
import com.leandro.market.domain.Purchase;

public interface PurchaseRepository {
    List<Purchase> getAll();
    //es probable que algunos clientes no tengan compras
    //por eso usamos Optional
    Optional<List<Purchase>> getByClient(String clientId);
    Purchase save(Purchase purchase);
}
