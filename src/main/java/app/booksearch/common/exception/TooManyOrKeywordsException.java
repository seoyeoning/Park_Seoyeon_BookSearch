package app.booksearch.common.exception;

public class TooManyOrKeywordsException extends RuntimeException {
    public TooManyOrKeywordsException(String message) {
        super(message);
    }
}
