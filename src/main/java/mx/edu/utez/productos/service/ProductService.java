package mx.edu.utez.productos.service;

import mx.edu.utez.productos.model.Product;
import mx.edu.utez.productos.repository.ProductRepository;

import mx.edu.utez.productos.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import mx.edu.utez.productos.model.Category;;

@Service 
public class ProductService {
    private final ProductRepository repo;
    private final CategoryRepository repoCategory;

    public ProductService(ProductRepository repo, CategoryRepository repoCategory){
        this.repo = repo;
        this.repoCategory = repoCategory;
    }

    public List<Product> getAll(){
        return repo.findAll();
    }

    public Optional<Product> findById(Long id){
        return repo.findById(id);
    }

    public Product save(Product product, Long categoryId){
        Category category = repoCategory.findById(categoryId).orElseThrow();//Brujeria paira arrojar un error, cuando no encuentre un optional para categoty
        //entonces arroja un error, evitando que el producto se cree
        product.setCategory(category);
        return repo.save(product); 
    }

    public Optional<Product> update(Long id, Product product){
        Optional<Product> optional = repo.findById(id);
        if (optional.isEmpty()){
            return optional.empty();
        }
        Product productDb = optional.get();

        productDb.setName(product.getName());
        productDb.setPrice(product.getPrice());
        productDb.setStock(product.getStock());

        return Optional.of(repo.save(productDb));
    }

    public boolean delete(Long id){
        if(!repo.existsById(id)){
            return false;
        }
        repo.deleteById(id);
        return true;
    }

}
