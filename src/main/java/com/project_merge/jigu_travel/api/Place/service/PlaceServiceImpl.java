package com.project_merge.jigu_travel.api.Place.service;

import com.project_merge.jigu_travel.api.Place.entity.Place;
import com.project_merge.jigu_travel.api.Place.repository.PlaceRepository;
import com.project_merge.jigu_travel.api.websocket.dto.responseDto.PlaceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.project_merge.jigu_travel.global.common.PlaceType;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    @Override
    public List<PlaceResponseDto> findNearbyPlace(double latitude, double longitude, double radius) {
        List<Place> places = placeRepository.findNearbyPlace(latitude, longitude, radius);
        return places.stream()
                .map(this::toPlaceResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlaceResponseDto findPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .map(this::toPlaceResponseDto)
                .orElse(null);
    }

    private PlaceResponseDto toPlaceResponseDto(Place place) {
        return PlaceResponseDto.builder()
                .placeId(place.getPlaceId().intValue())
                .types(place.getTypeList().stream()
                        .map(PlaceType::valueOf)
                        .toList())
                .name(place.getName())
                .tel(place.getTel())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .address(place.getAddress())
                .deleted(place.isDeleted())
                .createdAt(place.getCreatedAt())
                .updatedAt(place.getUpdatedAt())
                .build();
    }



    @Transactional
    @Override
    public void uploadPlacesFromCsv(MultipartFile file) {
        try (CSVReader csvReader = new CSVReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            List<String[]> rows = csvReader.readAll();
            rows.remove(0); // 첫 번째 행 (헤더) 제거

            for (String[] row : rows) {
                Place place = mapToPlace(row);
                placeRepository.save(place);
                placeRepository.flush(); // ✅ 강제로 INSERT 진행
            }

        } catch (Exception e) {
            throw new RuntimeException("CSV 파일 처리 중 오류 발생", e);
        }
    }

    private Place mapToPlace(String[] data) {
        System.out.println("CSV 데이터: " + Arrays.toString(data));

        // CSV의 `type` 컬럼을 `List<String>`으로 변환
        List<String> placeTypes = Arrays.stream(data[5].split(","))
                .map(String::trim)
                .toList();

        System.out.println("변환된 PlaceType: " + placeTypes);

        // Place 객체 생성 시 List<String> 사용
        Place place = new Place(placeTypes, data[0], data[1],
                Double.parseDouble(data[3]),
                Double.parseDouble(data[4]), data[2]);

        System.out.println("저장될 Place 객체: " + place);
        return place;
    }

    @Override
    public List<PlaceResponseDto> findNearbyALLPlaces(double latitude, double longitude, double radius, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Place> places = placeRepository.findNearbyALLPlaces(latitude, longitude, radius, pageRequest);

        return places.getContent().stream()
                .map(this::toPlaceResponseDto)
                .collect(Collectors.toList());
    }


}
