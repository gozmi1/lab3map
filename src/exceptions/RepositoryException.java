package exceptions;

public class RepositoryException extends RuntimeException {

    /**
     * Constructs a new repository exception with the specified detail message.
     * @param message the detail message.
     */
    public RepositoryException(String message) {
        super(message);
    }

    /**
     * Constructs a new repository exception with the specified detail message and cause.
     * @param message the detail message.
     * @param cause the cause (e.g., an IOException).
     */
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
