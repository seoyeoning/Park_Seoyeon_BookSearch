package app.booksearch.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeywordDto {

    private String keyword;

    public static KeywordDto of(String keyword) {
        return new KeywordDto(keyword);
    }
}
