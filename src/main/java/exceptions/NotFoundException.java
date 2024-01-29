package exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Elemento con id " + id + " non trovato");
    }
}