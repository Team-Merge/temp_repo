package com.project_merge.jigu_travel.global.common;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum PlaceType {
//    식도락_여행, 오락_체험_여행, 힐링_여행, 역사_문화_여행, 쇼핑_여행, 캠핑_글램핑_여행;
//
//    /**
//     * CSV에서 읽은 문자열을 ENUM으로 변환
//     * (예: "역사 / 문화 여행, 힐링 여행" → ["역사_문화_여행", "힐링_여행"])
//     */
//    public static Set<PlaceType> fromString(String typeString) {
//        if (typeString == null || typeString.isEmpty()) return Set.of();
//
//        return Arrays.stream(typeString.split(","))
//                .map(String::trim)
//                .map(type -> type.replace(" / ", "_")  // " / " → "_"
//                        .replaceAll(" ", "_")        // 공백 → "_"
//                        .replaceAll("__+", "_"))     // 연속된 "__" 이상을 "_"로 변환
//                .map(PlaceType::valueOf)
//                .collect(Collectors.toSet());
//    }
}