package mx.edu.utez.productos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mx.edu.utez.productos.exception.ResoureceNotFoundException;
import mx.edu.utez.productos.model.Category;
import mx.edu.utez.productos.repository.CategoryRepository;

@Service 
public class CategoryService {

    private final CategoryRepository repoCategory;

    public CategoryService(CategoryRepository repoCategory){
        this.repoCategory = repoCategory;
    }

    public List<Category> getAll(){
        return repoCategory.findAll();
    }

    public Optional<Category> findById(Long id){
        return repoCategory.findById(id);
    }

    public Category save(Category category){
        return repoCategory.save(category);
    }

    public Optional<Category> update(Long id, Category category){
        Category categoryDb = repoCategory.findById(id).orElseThrow(
            ()->
            new ResoureceNotFoundException("Category not found with id: " + id)
        );

        categoryDb.setName(category.getName());
        return Optional.of(repoCategory.save(categoryDb));
    }

    public boolean delete(Long id){
        if (!repoCategory.existsById(id)){
            return false;
        }
        repoCategory.deleteById(id);
        return true;
    }
}
