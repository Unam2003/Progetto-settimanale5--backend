package emanuelepiemonte.Progetto_settimanale5__backend.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("Il record con id " + id + " non è stato trovato correttamente");
    }
}
