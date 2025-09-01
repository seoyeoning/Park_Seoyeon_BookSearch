package app.booksearch.catalog.controller;

import app.booksearch.catalog.dto.BookListResponseDto;
import app.booksearch.catalog.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping
    public ResponseEntity<BookListResponseDto> getAllBooks(@RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok().body(catalogService.getAllBooks(page, size));
    }

}
