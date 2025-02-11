package com.project_merge.jigu_travel.api.Place.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_merge.jigu_travel.api.websocket.dto.requestDto.LocationRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Lob
    private String locations;

    private LocalDateTime timestamp = LocalDateTime.now(); // 자동 생성 시간

    // List<LocationRequestDto>를 JSON 문자열로 변환
    public void setLocations(List<LocationRequestDto> locationList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.locations = objectMapper.writeValueAsString(locationList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 오류", e);
        }
    }

    // JSON 데이터를 List<LocationRequestDto>로 변환하여 반환
    public List<LocationRequestDto> getLocationsAsList() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(this.locations,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, LocationRequestDto.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 파싱 오류", e);
        }
    }
}
