package com.project_merge.jigu_travel.api.image.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ImageResponseDto {
    private String code;
    private String message;
    private DetectionData data;  // data는 DetectionData 타입으로 수정

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetectionData {
        private List<Detection> detections; // detections 배열
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detection {
        private String className;
        private double confidence;
        private int x1;
        private int y1;
        private int x2;
        private int y2;
    }
}
