package mx.edu.utez.productos.controller;

import jakarta.validation.Valid;
import mx.edu.utez.productos.dto.ProductCambioRequest;
import mx.edu.utez.productos.dto.ProductRequest;
import mx.edu.utez.productos.dto.ProductResponse;
import mx.edu.utez.productos.exception.ResoureceNotFoundException;
import mx.edu.utez.productos.model.Product;
import mx.edu.utez.productos.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@RestController
@RequestMapping("/products")

public class ProductController {
    private ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> findAll(){
        List<ProductResponse> listResponse = new ArrayList<>();
        for (Product p : service.getAll()){
            listResponse.add(toResponse(p));
        }
        return ResponseEntity.ok(listResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id){
        Product product = service.findById(id).orElseThrow(
            ()->
            new ResoureceNotFoundException("Product not found with id: " +id)
        );
        //product es una entity y en este punto no es igual al Optional
           return ResponseEntity.ok(toResponse(product));
    }

    @PostMapping()
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductRequest product){
        Product newProduct = service.save(toEntity(product), product.categoryId());
       return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(newProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean deleted = service.delete(id);
        if (deleted){
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity <ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest productUpdate){
        Product product = service.update(
            id, toEntity(productUpdate),
            productUpdate.categoryId()
        );
        return ResponseEntity.ok(toResponse(product));
        /*Product product = service.update(
            id, toEntity(product),
            product.categoryId()
        );
       
       return ResponseEntity.ok(toResponse(product));*/
    }

    @PutMapping("/{id}/modifier")
    public ResponseEntity <ProductResponse> modifier(@PathVariable Long id, @Valid @RequestBody ProductCambioRequest productModifier){
        Product product = service.modifier(
            id,
            productModifier.getPalabra(),
            productModifier.getStock()
        );
        return ResponseEntity.ok(toResponse(product));
    }

    private ProductResponse toResponse(Product product){
        Long categoryId = (product.getCategory() != null) ? product.getCategory().getId() : null;
        String categoryName = (product.getCategory() != null) ? product.getCategory().getName() : "";
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock(), categoryId, categoryName);
    }

    private Product toEntity(ProductRequest request){

        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());

        return product;
    }

}
