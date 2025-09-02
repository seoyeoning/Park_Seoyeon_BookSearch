package app.booksearch.search.dto;

import app.booksearch.catalog.dto.BookBasicInfoDto;
import app.booksearch.catalog.dto.BookPageInfoDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookSearchResponseDto {

    private String searchQuery;
    private BookPageInfoDto pageInfo;
    private List<BookBasicInfoDto> books;
    private SearchMetadataDto searchMetadata;

}
