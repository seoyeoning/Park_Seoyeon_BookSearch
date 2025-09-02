package app.booksearch.search.repository;

import app.booksearch.catalog.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SearchRepository extends JpaRepository<Book, Long> {

    @Query(value = """
            SELECT *
            FROM book
            WHERE MATCH(title, subtitle) AGAINST(:query IN BOOLEAN MODE)
            ORDER BY MATCH(title, subtitle) AGAINST(:query IN BOOLEAN MODE) DESC,
                     id DESC
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM book
            WHERE MATCH(title, subtitle) AGAINST(:query IN BOOLEAN MODE)
            """,
            nativeQuery = true)
    Page<Book> searchBooksBySingleKeywordOrNotQuery(@Param("query") String query, Pageable pageable);

    @Query(value = """
            SELECT *
            FROM book
            WHERE MATCH(title, subtitle) AGAINST(:keyword1 IN BOOLEAN MODE)
               OR MATCH(title, subtitle) AGAINST(:keyword2 IN BOOLEAN MODE)
            ORDER BY (
                MATCH(title, subtitle) AGAINST(:keyword1 IN BOOLEAN MODE) +
                MATCH(title, subtitle) AGAINST(:keyword2 IN BOOLEAN MODE)
            ) DESC,
            id DESC
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM book
            WHERE MATCH(title, subtitle) AGAINST(:keyword1 IN BOOLEAN MODE)
               OR MATCH(title, subtitle) AGAINST(:keyword2 IN BOOLEAN MODE)
            """,
            nativeQuery = true)
    Page<Book> searchBooksByOrQuery(@Param("keyword1") String keyword1,
            @Param("keyword2") String keyword2,
            Pageable pageable);
}
