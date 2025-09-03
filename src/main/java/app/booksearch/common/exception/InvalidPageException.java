package app.booksearch.common.exception;

public class InvalidPageException extends RuntimeException {
    public InvalidPageException(String message) {
        super(message);
    }
}
