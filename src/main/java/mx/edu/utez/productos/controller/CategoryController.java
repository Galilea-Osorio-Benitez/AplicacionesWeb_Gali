package mx.edu.utez.productos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mx.edu.utez.productos.dto.CategoryRequest;
import mx.edu.utez.productos.dto.CategoryResponse;
import mx.edu.utez.productos.model.Category;
import mx.edu.utez.productos.repository.CategoryRepository;
import mx.edu.utez.productos.service.CategoryService;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController 
@RequestMapping ("/categories")
public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryService service){
        this.service = service;
    }

    @GetMapping 
    public ResponseEntity<List<CategoryResponse>> findAll(){
        List<CategoryResponse> listResponse = new ArrayList<>();
        for(Category c : service.getAll()){
            listResponse.add(toResponse(c));
        }
        return ResponseEntity.ok(listResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id){
        Optional<Category> category = service.findById(id);
        if (category.isPresent()){
            return ResponseEntity.ok(toResponse(category.get()));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping 
    public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest category){
        Category newCategory = service.save(toEntity(category));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(newCategory));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping ("/{id}")
    public ResponseEntity <CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest category){
        Optional<Category> optional = service.update(
            id, toEntity(category));
            if (optional.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(toResponse(optional.get()));
            }
            return ResponseEntity.notFound().build();
    }

    private CategoryResponse toResponse(Category category){
        return new CategoryResponse(category.getId(), category.getName());
    }

    private Category toEntity(CategoryRequest request){
        Category category = new Category();
        category.setName(request.name());

        return category;
    }

}
