package app.booksearch.common.exception;

public class QueryTooLongException extends RuntimeException {
    public QueryTooLongException(String message) {
        super(message);
    }
}
