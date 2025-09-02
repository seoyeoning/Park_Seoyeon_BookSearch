package app.booksearch.search.controller;
import app.booksearch.search.dto.KeywordDto;
import app.booksearch.search.service.TrendingKeywordService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trend")
public class TrendingKeywordController {

    private final TrendingKeywordService trendingKeywordService;

    @GetMapping("/search/keywords")
    public ResponseEntity<List<KeywordDto>> getTop10TrendingKeywords() {
        return ResponseEntity.ok().body(trendingKeywordService.getTop10TrendingKeywords());
    }
}
