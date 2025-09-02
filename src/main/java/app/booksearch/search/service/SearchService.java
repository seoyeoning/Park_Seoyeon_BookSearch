package app.booksearch.search.service;

import app.booksearch.catalog.domain.Book;
import app.booksearch.catalog.dto.BookBasicInfoDto;
import app.booksearch.catalog.dto.BookPageInfoDto;
import app.booksearch.search.dto.BookSearchResponseDto;
import app.booksearch.search.dto.SearchMetadataDto;
import app.booksearch.search.dto.SearchStrategy;
import app.booksearch.search.repository.SearchRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {

    private final SearchRepository searchRepository;
    private final TrendingKeywordService trendingKeywordService;

    public BookSearchResponseDto getBooksByQuery(String query, int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        SearchStrategy searchStrategy = checkSearchStrategy(query);

        // 검색어 저장
        trendingKeywordService.saveSearchKeyword(searchStrategy, query);

        Page<Book> searchResults;

        switch (searchStrategy) {
            case SINGLE_KEYWORD -> {
                String q = queryForSingleKeyword(query);
                searchResults = searchRepository.searchBooksBySingleKeywordOrNotQuery(q, pageable);
            }
            case NOT_OPERATION -> {
                String q = queryForNotOperation(query);
                searchResults = searchRepository.searchBooksBySingleKeywordOrNotQuery(q, pageable);
            }
            case OR_OPERATION -> {
                String[] qs = keywordsForOrOperation(query);
                searchResults = searchRepository.searchBooksByOrQuery(qs[0], qs[1], pageable);
            }
            default -> throw new IllegalStateException("Unexpected strategy: " + searchStrategy);
        }

        SearchMetadataDto searchMetadataDto = SearchMetadataDto.builder()
                .strategy(searchStrategy.name())
                .build();

        List<BookBasicInfoDto> books = searchResults.getContent().stream()
                .map(BookBasicInfoDto::from)
                .toList();

        BookPageInfoDto pageInfo = BookPageInfoDto.builder()
                .currentPage(searchResults.getNumber() + 1)
                .pageSize(searchResults.getSize())
                .totalPages(searchResults.getTotalPages())
                .totalElements(searchResults.getTotalElements())
                .hasNext(searchResults.hasNext())
                .hasPrev(searchResults.hasPrevious())
                .build();

        return BookSearchResponseDto.builder()
                .searchQuery(query)
                .pageInfo(pageInfo)
                .books(books)
                .searchMetadata(searchMetadataDto)
                .build();
    }

    // 전략 정하기 메서드
    public SearchStrategy checkSearchStrategy(String query) {
        // 전략이 없으면 예외 처리!
        String q = query.trim();
        if (q.contains("|")) return SearchStrategy.OR_OPERATION;
        if (q.contains("-")) return SearchStrategy.NOT_OPERATION;
        return SearchStrategy.SINGLE_KEYWORD;
    }

    // 단순 키워드 검색 "spring boot" → "+spring +boot"
    public String queryForSingleKeyword(String query) {
        return Arrays.stream(query.trim().split("\\s+"))
                .filter(s -> !s.isBlank())
                .map(t -> "+" + t)
                .collect(Collectors.joining(" "));
    }

    // OR 연산 검색 "spring boot|java" → ["+spring +boot", "+java"]
    public String[] keywordsForOrOperation(String query) {
        String[] groups = query.split("\\|");
        String[] keywords = new String[2];

        for (int i = 0; i < 2; i++) {
            keywords[i] = Arrays.stream(groups[i].trim().split("\\s+"))
                    .filter(s -> !s.isBlank())
                    .map(t -> "+" + t)
                    .collect(Collectors.joining(" "));
        }
        return keywords;
    }

    // NOT 연산 검색 "spring boot-java" -> "+spring +boot -java"
    public String queryForNotOperation(String query) {
        String[] groups = query.split("-");
        String resultQuery = "";

        resultQuery += Arrays.stream(groups[0].trim().split("\\s+"))
                .filter(s -> !s.isBlank())
                .map(t -> "+" + t)
                .collect(Collectors.joining(" "));

        resultQuery += Arrays.stream(groups[1].trim().split("\\s+"))
                .filter(s -> !s.isBlank())
                .map(t -> "-" + t)
                .collect(Collectors.joining(" "));

        return resultQuery;
    }
}
