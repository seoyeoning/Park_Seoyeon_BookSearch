package app.booksearch.common.validator;

import app.booksearch.common.exception.*;
import org.springframework.stereotype.Component;

@Component
public class SearchQueryValidator {

    private static final int MAX_QUERY_LENGTH = 200;
    private static final int MIN_QUERY_LENGTH = 3;
    private static final int MAX_SIZE = 10;
    private static final int MIN_SIZE = 1;

    public void validatePage(int page) {
        if (page < 0) {
            throw new InvalidPageException("페이지는 0이상의 양수만 가능합니다.");
        }
    }

    public void validateSize(int size) {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new InvalidSizeException("size는 1~10사이만 가능합니다.");
        }
    }

    public void validateQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new EmptyQueryException("쿼리를 입력해주세요.");
        }

        String trimmedQuery = query.trim();
        
        if (trimmedQuery.length() > MAX_QUERY_LENGTH) {
            throw new QueryTooLongException("쿼리 길이는 200자를 초과할 수 없습니다.");
        }

        if (trimmedQuery.length() < MIN_QUERY_LENGTH) {
            throw new QueryTooShortException("3자 이상부터 검색 가능합니다.");
        }

        // OR, NOT 연산자 혼용 검사
        boolean hasOr = trimmedQuery.contains("|");
        boolean hasNot = trimmedQuery.contains("-");
        
        if (hasOr && hasNot) {
            throw new MixedSearchStrategyException("OR 검색 또는 NOT 검색중 1개만 가능합니다.");
        }

        // OR 검색 유효성 검사
        if (hasOr) {
            validateOrQuery(trimmedQuery);
        }

        // NOT 검색 유효성 검사
        if (hasNot) {
            validateNotQuery(trimmedQuery);
        }
    }

    private void validateOrQuery(String query) {
        String[] parts = query.split("\\|");
        
        if (parts.length != 2) {
            throw new TooManyOrKeywordsException("키워드는 2개까지 가능합니다.");
        }

        String keyword1 = parts[0].trim();
        String keyword2 = parts[1].trim();

        if (keyword1.isEmpty() || keyword2.isEmpty()) {
            throw new InvalidOrQueryException("올바른 쿼리가 아닙니다.");
        }

        if (keyword1.equalsIgnoreCase(keyword2)) {
            throw new DuplicateKeywordException("같은 키워드 2개로 안됩니다.");
        }
    }

    private void validateNotQuery(String query) {
        String[] parts = query.split("-");
        
        if (parts.length != 2) {
            throw new TooManyNotKeywordsException("키워드는 2개까지 가능합니다.");
        }

        String keyword1 = parts[0].trim();
        String keyword2 = parts[1].trim();

        if (keyword1.isEmpty() || keyword2.isEmpty()) {
            throw new InvalidNotQueryException("올바른 쿼리가 아닙니다.");
        }

        if (keyword1.equalsIgnoreCase(keyword2)) {
            throw new DuplicateKeywordException("같은 키워드 2개로 안됩니다.");
        }
    }
}
