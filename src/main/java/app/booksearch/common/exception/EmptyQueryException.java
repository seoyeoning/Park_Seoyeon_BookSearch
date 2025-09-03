package app.booksearch.common.exception;

public class EmptyQueryException extends RuntimeException {
    public EmptyQueryException(String message) {
        super(message);
    }
}
