package app.booksearch.common.validator;

import app.booksearch.common.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SearchQueryValidatorTest {

    private SearchQueryValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SearchQueryValidator();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 10})
    @DisplayName("페이지 유효성 검사 - 정상 케이스")
    void validatePage_ValidPage_ShouldNotThrowException(int validPage) {
        // when & then
        assertDoesNotThrow(() -> validator.validatePage(validPage));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10})
    @DisplayName("페이지 유효성 검사 - 음수")
    void validatePage_InvalidPage_ShouldThrowException(int invalidPage) {
        // when & then
        assertThrows(InvalidPageException.class, () -> validator.validatePage(invalidPage));
    }

    @Test
    @DisplayName("크기 유효성 검사 - 정상 케이스")
    void validateSize_ValidSize_ShouldNotThrowException() {
        // given
        int validSize = 5;

        // when & then
        assertDoesNotThrow(() -> validator.validateSize(validSize));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 11, 100})
    @DisplayName("크기 유효성 검사 - 범위 벗어남")
    void validateSize_InvalidSize_ShouldThrowException(int invalidSize) {
        // when & then
        assertThrows(InvalidSizeException.class, () -> validator.validateSize(invalidSize));
    }

    @Test
    @DisplayName("쿼리 유효성 검사 - 정상 키워드")
    void validateQuery_ValidKeyword_ShouldNotThrowException() {
        // given
        String validQuery = "spring boot";

        // when & then
        assertDoesNotThrow(() -> validator.validateQuery(validQuery));
    }

    @Test
    @DisplayName("쿼리 유효성 검사 - 빈 쿼리")
    void validateQuery_EmptyQuery_ShouldThrowException() {
        // given
        String emptyQuery = "";

        // when & then
        assertThrows(EmptyQueryException.class, () -> validator.validateQuery(emptyQuery));
    }

    @Test
    @DisplayName("쿼리 유효성 검사 - null 쿼리")
    void validateQuery_NullQuery_ShouldThrowException() {
        // when & then
        assertThrows(EmptyQueryException.class, () -> validator.validateQuery(null));
    }

    @Test
    @DisplayName("쿼리 유효성 검사 - 너무 짧은 쿼리")
    void validateQuery_TooShortQuery_ShouldThrowException() {
        // given
        String shortQuery = "ab";

        // when & then
        assertThrows(QueryTooShortException.class, () -> validator.validateQuery(shortQuery));
    }

    @Test
    @DisplayName("쿼리 유효성 검사 - 너무 긴 쿼리")
    void validateQuery_TooLongQuery_ShouldThrowException() {
        // given
        String longQuery = "a".repeat(201);

        // when & then
        assertThrows(QueryTooLongException.class, () -> validator.validateQuery(longQuery));
    }

    @Test
    @DisplayName("OR 검색 유효성 검사 - 정상 OR 쿼리")
    void validateQuery_ValidOrQuery_ShouldNotThrowException() {
        // given
        String validOrQuery = "spring|java";

        // when & then
        assertDoesNotThrow(() -> validator.validateQuery(validOrQuery));
    }

    @Test
    @DisplayName("OR 검색 유효성 검사 - 잘못된 OR 쿼리 형식")
    void validateQuery_InvalidOrQuery_ShouldThrowException() {
        // given
        String invalidOrQuery = "spring|";

        // when & then
        assertThrows(TooManyOrKeywordsException.class, () -> validator.validateQuery(invalidOrQuery));
    }

    @Test
    @DisplayName("OR 검색 유효성 검사 - 키워드 3개 초과")
    void validateQuery_TooManyOrKeywords_ShouldThrowException() {
        // given
        String tooManyKeywords = "spring|java|python";

        // when & then
        assertThrows(TooManyOrKeywordsException.class, () -> validator.validateQuery(tooManyKeywords));
    }

    @Test
    @DisplayName("NOT 검색 유효성 검사 - 정상 NOT 쿼리")
    void validateQuery_ValidNotQuery_ShouldNotThrowException() {
        // given
        String validNotQuery = "spring-java";

        // when & then
        assertDoesNotThrow(() -> validator.validateQuery(validNotQuery));
    }

    @Test
    @DisplayName("NOT 검색 유효성 검사 - 잘못된 NOT 쿼리 형식")
    void validateQuery_InvalidNotQuery_ShouldThrowException() {
        // given
        String invalidNotQuery = "spring-";

        // when & then
        assertThrows(TooManyNotKeywordsException.class, () -> validator.validateQuery(invalidNotQuery));
    }

    @Test
    @DisplayName("검색 전략 혼용 검사")
    void validateQuery_MixedSearchStrategy_ShouldThrowException() {
        // given
        String mixedQuery = "spring|java-python";

        // when & then
        assertThrows(MixedSearchStrategyException.class, () -> validator.validateQuery(mixedQuery));
    }

    @Test
    @DisplayName("중복 키워드 검사")
    void validateQuery_DuplicateKeywords_ShouldThrowException() {
        // given
        String duplicateQuery = "spring|spring";

        // when & then
        assertThrows(DuplicateKeywordException.class, () -> validator.validateQuery(duplicateQuery));
    }
}
