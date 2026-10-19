package mx.edu.utez.productos.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
    @NotBlank 
    String name
){
    
}