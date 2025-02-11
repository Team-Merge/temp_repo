package com.project_merge.jigu_travel.api.Place.service;

import com.project_merge.jigu_travel.api.Place.dto.requestDto.PlaceUpdateRequestDto;
import com.project_merge.jigu_travel.api.Place.entity.Place;
import com.project_merge.jigu_travel.api.Place.repository.PlaceRepository;
import com.project_merge.jigu_travel.api.websocket.dto.responseDto.PlaceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import com.opencsv.CSVReader;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import com.project_merge.jigu_travel.api.Place.dto.responseDto.CategoryCountDto;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    @Override
    public List<PlaceResponseDto> findNearbyPlace(double latitude, double longitude, double radius, List<String> types) {
        List<Place> places;

        // 📌 관심사가 null이거나 빈 리스트이면 기본 장소 검색 실행
        if (types == null || types.isEmpty()) {
            places = placeRepository.findNearbyPlace(latitude, longitude, radius);
        } else {
            // 관심사 기반으로 필터링
            String types1 = (types.size() > 0) ? types.get(0) : null;
            String types2 = (types.size() > 1) ? types.get(1) : null;
            String combinedTypes = (types1 != null && types2 != null) ? types1 + "," + types2 : null;
            places = placeRepository.findNearbyPlaceByTypes(latitude, longitude, radius, types1, types2, combinedTypes);
        }

        return places.stream()
                .map(this::toPlaceResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlaceResponseDto> findNearbyPlace(double latitude, double longitude, double radius) {
        return findNearbyPlace(latitude, longitude, radius, new ArrayList<>()); // 빈 리스트를 넘김
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
                .types(place.getTypeList())
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
                Place newPlace = mapToPlace(row);

                // 기존 데이터 확인 (name, address 동일한 데이터 존재 여부)
                Optional<Place> existingPlaceOpt = placeRepository.findByNameAndAddress(newPlace.getName(), newPlace.getAddress());

                if (existingPlaceOpt.isPresent()) {
                    // 기존 데이터가 있으면 최신 데이터로 업데이트
                    Place existingPlace = existingPlaceOpt.get();
                    updateExistingPlace(existingPlace, newPlace);
                    placeRepository.save(existingPlace);
                } else {
                    // 기존 데이터가 없으면 새로운 장소 추가
                    placeRepository.save(newPlace);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("CSV 파일 처리 중 오류 발생", e);
        }
    }

    /** 기존 장소 업데이트 */
    private void updateExistingPlace(Place existingPlace, Place newPlace) {
        existingPlace.setTypes(newPlace.getTypes());
        existingPlace.setTel(newPlace.getTel());
        existingPlace.setLatitude(newPlace.getLatitude());
        existingPlace.setLongitude(newPlace.getLongitude());
    }


    private Place mapToPlace(String[] data) {
        // CSV의 `type` 컬럼을 `List<String>`으로 변환
        List<String> placeTypes = Arrays.stream(data[5].split(","))
                .map(String::trim)
                .toList();

        // Place 객체 생성 시 List<String> 사용
        Place place = new Place(placeTypes, data[0], data[1],
                Double.parseDouble(data[3]),
                Double.parseDouble(data[4]), data[2]);

        return place;
    }

    @Override
    public Page<PlaceResponseDto> findAllPlaces(boolean includeDeleted, int page, int size, String searchQuery, String searchBy) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Place> places;

        // 검색어가 없으면 전체 조회
        if (searchQuery == null || searchQuery.isEmpty()) {
            places = includeDeleted ? placeRepository.findAll(pageable) : placeRepository.findByDeletedFalse(pageable);
        } else {
            // 검색 기준에 따라 조회
            switch (searchBy) {
                case "name":
                    places = includeDeleted ? placeRepository.findByNameContainingIgnoreCase(searchQuery, pageable)
                            : placeRepository.findByNameContainingIgnoreCaseAndDeletedFalse(searchQuery, pageable);
                    break;
                case "types":
                    places = includeDeleted ? placeRepository.findByTypesContainingIgnoreCase(searchQuery, pageable)
                            : placeRepository.findByTypesContainingIgnoreCaseAndDeletedFalse(searchQuery, pageable);
                    break;
                case "address":
                    places = includeDeleted ? placeRepository.findByAddressContainingIgnoreCase(searchQuery, pageable)
                            : placeRepository.findByAddressContainingIgnoreCaseAndDeletedFalse(searchQuery, pageable);
                    break;
                default:
                    places = includeDeleted ? placeRepository.findAll(pageable) : placeRepository.findByDeletedFalse(pageable);
                    break;
            }
        }

        return places.map(this::toPlaceResponseDto);
    }

    @Override
    public Page<PlaceResponseDto> findNearbyALLPlaces(double latitude, double longitude, double radius, int page, int size, boolean includeDeleted) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Place> places = includeDeleted ?
                placeRepository.findNearbyALLPlacesIncludingDeleted(latitude, longitude, radius, pageRequest) :
                placeRepository.findNearbyALLPlaces(latitude, longitude, radius, pageRequest);

        return places.map(this::toPlaceResponseDto);
    }


    @Transactional
    @Override
    public PlaceResponseDto updatePlace(Long placeId, PlaceUpdateRequestDto updateDto) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "장소를 찾을 수 없습니다."));

        // 필드 업데이트 (null 값이 아닌 경우만)
        if (updateDto.getName() != null) place.setName(updateDto.getName());
        if (updateDto.getAddress() != null) place.setAddress(updateDto.getAddress());
        if (updateDto.getTypes() != null) place.setTypes(String.join(",", updateDto.getTypes()));
        if (updateDto.getTel() != null) place.setTel(updateDto.getTel());
        if (updateDto.getLatitude() != null) place.setLatitude(updateDto.getLatitude());
        if (updateDto.getLongitude() != null) place.setLongitude(updateDto.getLongitude());

        placeRepository.save(place);

        return toPlaceResponseDto(place);
    }

    @Transactional
    @Override
    public Boolean toggleDeletePlace(Long placeId) {
        Optional<Place> placeOptional = placeRepository.findById(placeId);

        if (placeOptional.isEmpty()) {
            return null; // 404 반환용
        }

        Place place = placeOptional.get();
        boolean newStatus = !place.isDeleted(); // 현재 상태 반전
        place.setDeleted(newStatus);
        placeRepository.save(place);

        return newStatus;
    }

    @Override
    @Transactional
    public boolean permanentlyDeletePlace(Long placeId) {
        Optional<Place> placeOptional = placeRepository.findById(placeId);

        if (placeOptional.isEmpty()) {
            return false; // 404 처리용
        }

        placeRepository.deleteById(placeId); // 완전 삭제
        return true;
    }

    @Override
    @Transactional
    public Page<PlaceResponseDto> findDeletedPlaces(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Place> places = placeRepository.findDeletedPlaces(pageable);
        return places.map(this::toPlaceResponseDto); // Page 그대로 반환
    }

    @Override
    public List<CategoryCountDto> getPlacesCountByCategory() {
        Object[] result = placeRepository.countPlacesByCategory().get(0);

        List<CategoryCountDto> categoryCounts = new ArrayList<>();
        String[] categoryNames = {
                "식도락_여행", "오락_체험_여행", "힐링_여행", "역사_문화_여행", "쇼핑_여행", "캠핑_글램핑_여행"
        };

        for (int i = 0; i < categoryNames.length; i++) {
            categoryCounts.add(new CategoryCountDto(categoryNames[i], ((Number) result[i]).longValue()));
        }

        return categoryCounts;
    }


}
