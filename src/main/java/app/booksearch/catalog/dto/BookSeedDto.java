package app.booksearch.catalog.dto;

import app.booksearch.catalog.domain.Book;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BookSeedDto {

    private final String isbn;
    private final String title;
    private final String subtitle;
    private final String bookCoverImageUrl;
    private final String author;
    private final String publisher;
    private final Integer published;
    private final Integer rating;
    private final String description;
    private final BigDecimal price;

    @JsonCreator
    public BookSeedDto(
            @JsonProperty("isbn") String isbn,
            @JsonProperty("title") String title,
            @JsonProperty("subtitle") String subtitle,
            @JsonProperty("book_cover_image_url") String bookCoverImageUrl,
            @JsonProperty("author") String author,
            @JsonProperty("publisher") String publisher,
            @JsonProperty("published") Integer published,
            @JsonProperty("rating") Integer rating,
            @JsonProperty("description") String description,
            @JsonProperty("price") BigDecimal price
    ) {
        this.isbn = isbn;
        this.title = title;
        this.subtitle = subtitle;
        this.bookCoverImageUrl = bookCoverImageUrl;
        this.author = author;
        this.publisher = publisher;
        this.published = published;
        this.rating = rating;
        this.description = description;
        this.price = price;
    }

    public Book toEntity() {
        return Book.builder()
                .isbn(isbn)
                .title(title)
                .subtitle(subtitle)
                .bookCoverImageUrl(bookCoverImageUrl)
                .author(author)
                .publisher(publisher)
                .published(published)
                .rating(rating)
                .description(description)
                .price(price)
                .build();
    }

}
