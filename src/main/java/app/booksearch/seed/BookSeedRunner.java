package app.booksearch.seed;

import app.booksearch.catalog.dto.BookSeedDto;
import app.booksearch.catalog.repository.CatalogRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookSeedRunner implements ApplicationRunner {

    private final CatalogRepository catalogRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (catalogRepository.count() > 0) return;

        try (InputStream is = new ClassPathResource("seed/books.json").getInputStream()) {
            List<BookSeedDto> seeds = objectMapper.readValue(is, new TypeReference<>() {});
            catalogRepository.saveAll(
                    seeds.stream()
                            .map(BookSeedDto::toEntity)
                            .toList()
            );
            log.info("도서 데이터 {}개 DB 저장 완료", catalogRepository.count());
        }
    }
}
