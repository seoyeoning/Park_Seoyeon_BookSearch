package app.booksearch.catalog.dto;

import app.booksearch.catalog.domain.Book;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookBasicInfoDto {

    private Long id;
    private String isbn;
    private String title;
    private String bookCoverImageUrl;
    private String author;
    private Integer rating;
    private BigDecimal price;

    public static BookBasicInfoDto from(Book b) {
        return new BookBasicInfoDto(
                b.getId(),
                b.getIsbn(),
                b.getTitle(),
                b.getBookCoverImageUrl(),
                b.getAuthor(),
                b.getRating(),
                b.getPrice()
        );
    }

}
