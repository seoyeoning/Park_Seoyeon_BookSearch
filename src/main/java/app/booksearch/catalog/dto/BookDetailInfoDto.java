package app.booksearch.catalog.dto;

import app.booksearch.catalog.domain.Book;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDetailInfoDto {

    private Long id;
    private String isbn;
    private String title;
    private String bookCoverImageUrl;
    private String author;
    private Integer rating;
    private BigDecimal price;
    private String subtitle;
    private String publisher;
    private Integer published;
    private String description;

    public static BookDetailInfoDto from(Book b) {
        return BookDetailInfoDto.builder()
                .id(b.getId())
                .isbn(b.getIsbn())
                .title(b.getTitle())
                .bookCoverImageUrl(b.getBookCoverImageUrl())
                .author(b.getAuthor())
                .rating(b.getRating())
                .price(b.getPrice())
                .subtitle(b.getSubtitle())
                .publisher(b.getPublisher())
                .published(b.getPublished())
                .description(b.getDescription())
                .build();
    }

}
