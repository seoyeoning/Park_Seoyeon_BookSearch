package app.booksearch.common.exception;

public class TooManyNotKeywordsException extends RuntimeException {
    public TooManyNotKeywordsException(String message) {
        super(message);
    }
}
