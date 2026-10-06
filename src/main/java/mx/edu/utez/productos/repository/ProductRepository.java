package mx.edu.utez.productos.repository;

import mx.edu.utez.productos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
