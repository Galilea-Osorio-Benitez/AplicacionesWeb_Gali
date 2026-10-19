package mx.edu.utez.productos.exception;

public class InsuficienteStockException extends RuntimeException {
    public InsuficienteStockException(String message){
        super(message);
    }

}
