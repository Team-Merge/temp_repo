package com.project_merge.jigu_travel.api.ai_classification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class RecommendationResponseDto {
    private int code;
    private String message;

    @JsonProperty("data")
    private RecommendationData data;

    @Data
    public static class RecommendationData {
        @JsonProperty("category_scores")
        private Map<String, Double> categoryScores;

        @JsonProperty("top2_recommendations")
        private List<String> top2Recommendations;
    }
}
