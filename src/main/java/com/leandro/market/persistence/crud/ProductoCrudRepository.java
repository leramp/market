package com.leandro.market.persistence.crud;

import com.leandro.market.persistence.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {
    List<Producto> findByIdCategoriaOrderByNombreAsc(int idCategoria);
   //esto mismo lo puedo hacer con una query nativa y además puedo poner otro nombre al método
//    @Query(value="SELECT * FROM productos WHERE id_categoria = ?", nativeQuery = true)
//    List<Producto> findByIdCat(int idCategoria);
    //tambien soportan los operadores optionales
   Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidadStock, boolean estado);
}
