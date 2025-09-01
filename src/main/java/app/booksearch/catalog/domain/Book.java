package app.booksearch.catalog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String isbn;

    private String title;

    private String subtitle;

    private String bookCoverImageUrl;

    private String author;

    private String publisher;

    private Integer published;

    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @lombok.Builder
    public Book(String isbn, String title, String subtitle, String bookCoverImageUrl,
            String author, String publisher, Integer published,
            Integer rating, String description, BigDecimal price) {
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

}