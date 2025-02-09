package com.project_merge.jigu_travel.api.Place.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceUpdateRequestDto {
    private String name;
    private String address;
    private List<String> types;
    private String tel;
    private Double latitude;
    private Double longitude;
}
