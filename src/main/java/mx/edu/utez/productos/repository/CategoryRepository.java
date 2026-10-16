package mx.edu.utez.productos.repository;
import mx.edu.utez.productos.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
