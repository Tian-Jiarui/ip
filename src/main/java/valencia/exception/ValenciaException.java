package valencia.exception;

/**
 * Represents an exception caused by invalid user input or invalid command format.
 */
public class ValenciaException extends RuntimeException {

    /**
     * Creates a ValenciaException with the given error message.
     *
     * @param message Error message to show to the user.
     */
    public ValenciaException(String message) {
        super(message);
    }
}
