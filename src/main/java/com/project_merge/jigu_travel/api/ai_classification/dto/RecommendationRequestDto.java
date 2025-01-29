package com.project_merge.jigu_travel.api.ai_classification.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RecommendationRequestDto {
    private int age;
    private int gender; // 1: 남성, 0: 여성
    private int annual_travel_frequency; // FastAPI와 동일한 필드명 사용 (snake_case)
    private List<String> selected_genres; // FastAPI와 동일한 필드명 사용 (snake_case)
}
