package app.booksearch.catalog.dto;

import app.booksearch.catalog.domain.Book;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookBasicInfoDto {

    private Long id;
    private String isbn;
    private String title;
    private String subtitle;
    private String bookCoverImageUrl;
    private String author;
    private Integer rating;
    private BigDecimal price;

    public static BookBasicInfoDto from(Book b) {
        return BookBasicInfoDto.builder()
                .id(b.getId())
                .isbn(b.getIsbn())
                .title(b.getTitle())
                .subtitle(b.getSubtitle())
                .bookCoverImageUrl(b.getBookCoverImageUrl())
                .author(b.getAuthor())
                .rating(b.getRating())
                .price(b.getPrice())
                .build();
    }

}
