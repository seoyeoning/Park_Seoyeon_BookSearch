package app.booksearch.controller;

import app.booksearch.common.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class SearchControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("도서 검색 API - 정상 케이스")
    void searchBooks_ValidQuery_ShouldReturnSuccess() throws Exception {
        // given
        String query = "spring";
        int page = 1;
        int size = 5;

        // when & then
        mockMvc.perform(get("/api/v1/search/books")
                        .param("query", query)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("도서 검색 API - page=0 정상 케이스")
    void searchBooks_PageZero_ShouldReturnSuccess() throws Exception {
        // given
        String query = "spring";
        int page = 0;
        int size = 5;

        // when & then
        mockMvc.perform(get("/api/v1/search/books")
                        .param("query", query)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("도서 검색 API - 잘못된 페이지")
    void searchBooks_InvalidPage_ShouldReturnBadRequest() throws Exception {
        // given
        String query = "spring";
        int invalidPage = -1;
        int size = 5;

        // when & then
        mockMvc.perform(get("/api/v1/search/books")
                        .param("query", query)
                        .param("page", String.valueOf(invalidPage))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("페이지는 0이상의 양수만 가능합니다."))
                .andExpect(jsonPath("$.errorCode").value("INVALID_PAGE"));
    }

    @Test
    @DisplayName("도서 검색 API - 잘못된 크기")
    void searchBooks_InvalidSize_ShouldReturnBadRequest() throws Exception {
        // given
        String query = "spring";
        int page = 1;
        int invalidSize = 11;

        // when & then
        mockMvc.perform(get("/api/v1/search/books")
                        .param("query", query)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(invalidSize))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("size는 1~10사이만 가능합니다."))
                .andExpect(jsonPath("$.errorCode").value("INVALID_SIZE"));
    }

    @Test
    @DisplayName("도서 검색 API - 빈 쿼리")
    void searchBooks_EmptyQuery_ShouldReturnBadRequest() throws Exception {
        // given
        String emptyQuery = "";
        int page = 1;
        int size = 5;

        // when & then
        mockMvc.perform(get("/api/v1/search/books")
                        .param("query", emptyQuery)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("쿼리를 입력해주세요."))
                .andExpect(jsonPath("$.errorCode").value("EMPTY_QUERY"));
    }

    @Test
    @DisplayName("도서 검색 API - OR 검색 정상 케이스")
    void searchBooks_ValidOrQuery_ShouldReturnSuccess() throws Exception {
        // given
        String orQuery = "spring|java";
        int page = 1;
        int size = 5;

        // when & then
        mockMvc.perform(get("/api/v1/search/books")
                        .param("query", orQuery)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchQuery").value(orQuery))
                .andExpect(jsonPath("$.searchMetadata.strategy").value("OR_OPERATION"));
    }

    @Test
    @DisplayName("도서 검색 API - NOT 검색 정상 케이스")
    void searchBooks_ValidNotQuery_ShouldReturnSuccess() throws Exception {
        // given
        String notQuery = "spring-java";
        int page = 1;
        int size = 5;

        // when & then
        mockMvc.perform(get("/api/v1/search/books")
                        .param("query", notQuery)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchQuery").value(notQuery))
                .andExpect(jsonPath("$.searchMetadata.strategy").value("NOT_OPERATION"));
    }
}
