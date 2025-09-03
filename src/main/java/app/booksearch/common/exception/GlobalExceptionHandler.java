package app.booksearch.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("잘못된 URL입니다.", "NO_HANDLER_FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidPageException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPageException(InvalidPageException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "INVALID_PAGE");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidSizeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSizeException(InvalidSizeException e) {
        ErrorResponse errorResponse = new ErrorResponse("size는 1~10사이만 가능합니다.", "INVALID_SIZE");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(QueryTooLongException.class)
    public ResponseEntity<ErrorResponse> handleQueryTooLongException(QueryTooLongException e) {
        ErrorResponse errorResponse = new ErrorResponse("쿼리 길이는 200자를 초과할 수 없습니다.", "QUERY_TOO_LONG");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EmptyQueryException.class)
    public ResponseEntity<ErrorResponse> handleEmptyQueryException(EmptyQueryException e) {
        ErrorResponse errorResponse = new ErrorResponse("쿼리를 입력해주세요.", "EMPTY_QUERY");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MixedSearchStrategyException.class)
    public ResponseEntity<ErrorResponse> handleMixedSearchStrategyException(MixedSearchStrategyException e) {
        ErrorResponse errorResponse = new ErrorResponse("OR 검색 또는 NOT 검색중 1개만 가능합니다.", "MIXED_SEARCH_STRATEGY");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicateKeywordException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateKeywordException(DuplicateKeywordException e) {
        ErrorResponse errorResponse = new ErrorResponse("같은 키워드 2개로 안됩니다.", "DUPLICATE_KEYWORD");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(QueryTooShortException.class)
    public ResponseEntity<ErrorResponse> handleQueryTooShortException(QueryTooShortException e) {
        ErrorResponse errorResponse = new ErrorResponse("3자 이상부터 검색 가능합니다.", "QUERY_TOO_SHORT");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidOrQueryException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOrQueryException(InvalidOrQueryException e) {
        ErrorResponse errorResponse = new ErrorResponse("올바른 쿼리가 아닙니다.", "INVALID_OR_QUERY");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(TooManyOrKeywordsException.class)
    public ResponseEntity<ErrorResponse> handleTooManyOrKeywordsException(TooManyOrKeywordsException e) {
        ErrorResponse errorResponse = new ErrorResponse("키워드는 2개까지 가능합니다.", "TOO_MANY_OR_KEYWORDS");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidNotQueryException.class)
    public ResponseEntity<ErrorResponse> handleInvalidNotQueryException(InvalidNotQueryException e) {
        ErrorResponse errorResponse = new ErrorResponse("올바른 쿼리가 아닙니다.", "INVALID_NOT_QUERY");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(TooManyNotKeywordsException.class)
    public ResponseEntity<ErrorResponse> handleTooManyNotKeywordsException(TooManyNotKeywordsException e) {
        ErrorResponse errorResponse = new ErrorResponse("키워드는 2개까지 가능합니다.", "TOO_MANY_NOT_KEYWORDS");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("서버 내부 오류가 발생했습니다.", "INTERNAL_SERVER_ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
