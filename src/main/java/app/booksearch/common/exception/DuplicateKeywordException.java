package app.booksearch.common.exception;

public class DuplicateKeywordException extends RuntimeException {
    public DuplicateKeywordException(String message) {
        super(message);
    }
}
