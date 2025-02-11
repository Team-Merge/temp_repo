package com.project_merge.jigu_travel.api.image.controller;

import com.project_merge.jigu_travel.api.image.service.FastImageService;
import com.project_merge.jigu_travel.api.image.dto.ImageResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final FastImageService fastImageService;

    public ImageController(FastImageService fastImageService) {
        this.fastImageService = fastImageService;
    }

    /**
     * 📌 FastAPI에 이미지 검색 요청
     */
    @PostMapping("/image_search")
    public ResponseEntity<ImageResponseDto> uploadImage(@RequestParam("file") MultipartFile file) {
        logger.info("Received image for search: {}", file.getOriginalFilename());
        ImageResponseDto response = fastImageService.processImageSearch(file);
        return ResponseEntity.ok(response);
    }
}
