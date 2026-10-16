package mx.edu.utez.productos.dto;

public record ProductResponse(
        Long id,
        String name,
        Double priece,
        int stock,
        Long categoryId,
        String categoryName
) {

}
