package com.project_merge.jigu_travel.api.websocket.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PlaceMessage {
    private Double latitude;
    private Double longitude;
}
