package app.booksearch.catalog.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookListResponseDto {

    private BookPageInfoDto pageInfo;
    private List<BookBasicInfoDto> books;

}
