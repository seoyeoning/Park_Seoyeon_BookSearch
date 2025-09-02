package app.booksearch.search.service;

import app.booksearch.search.dto.SearchStrategy;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrendingKeywordService {

    private final StringRedisTemplate redisTemplate;
    private static final String POPULAR_KEYWORDS_KEY = "trending_keywords";
    private static final Duration EXPIRATION_TIME = Duration.ofDays(7);

    public void saveSearchKeyword(SearchStrategy searchStrategy, String query) {

        List<String> keywords = extractKeywords(query.trim(), searchStrategy);

        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        for (String keyword : keywords) {
            if (keyword != null && !keyword.trim().isEmpty()) {
                zSetOps.incrementScore(POPULAR_KEYWORDS_KEY, keyword, 1.0);
            }
        }

        redisTemplate.expire(POPULAR_KEYWORDS_KEY, EXPIRATION_TIME);
    }

    private List<String> extractKeywords(String query, SearchStrategy searchStrategy) {

        return switch (searchStrategy) {
            case OR_OPERATION -> List.of(query.split("\\|"));
            case NOT_OPERATION -> List.of(query.split("-"));
            default -> List.of(query);
        };
    }



}
