package emanuelepiemonte.Progetto_settimanale5__backend.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
