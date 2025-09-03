package app.booksearch.common.exception;

public class QueryTooShortException extends RuntimeException {
    public QueryTooShortException(String message) {
        super(message);
    }
}
