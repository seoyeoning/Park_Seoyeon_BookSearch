package app.booksearch.catalog.repository;

import app.booksearch.catalog.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Book, Long> {

}
