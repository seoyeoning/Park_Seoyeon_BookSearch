package app.booksearch.search.controller;

import app.booksearch.search.dto.BookSearchResponseDto;
import app.booksearch.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/books")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<BookSearchResponseDto> getBooksByKeyword(@RequestParam String query,
            @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok().body(searchService.getBooksByQuery(query, page, size));
    }

}
