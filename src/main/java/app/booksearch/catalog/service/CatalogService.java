package app.booksearch.catalog.service;

import app.booksearch.catalog.domain.Book;
import app.booksearch.catalog.dto.BookBasicInfoDto;
import app.booksearch.catalog.dto.BookListResponseDto;
import app.booksearch.catalog.dto.BookPageInfoDto;
import app.booksearch.catalog.repository.CatalogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public BookListResponseDto getAllBooks(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Book> bookList = catalogRepository.findAll(pageable);

        List<BookBasicInfoDto> books = bookList.getContent().stream()
                .map(BookBasicInfoDto::from)
                .toList();

        BookPageInfoDto pageInfo = BookPageInfoDto.builder()
                .currentPage(bookList.getNumber() + 1)
                .pageSize(bookList.getSize())
                .totalPages(bookList.getTotalPages())
                .totalElements(bookList.getTotalElements())
                .hasNext(bookList.hasNext())
                .hasPrev(bookList.hasPrevious())
                .build();

        return new BookListResponseDto(pageInfo, books);
    }
}
