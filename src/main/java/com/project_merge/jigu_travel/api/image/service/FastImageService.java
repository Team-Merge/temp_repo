package com.project_merge.jigu_travel.api.image.service;

import com.project_merge.jigu_travel.api.image.dto.ImageResponseDto;
import com.project_merge.jigu_travel.api.image.fast.FastImageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FastImageService {

    private static final Logger logger = LoggerFactory.getLogger(FastImageService.class);
    private final FastImageClient fastImageClient;

    public FastImageService(FastImageClient fastImageClient) {
        this.fastImageClient = fastImageClient;
    }

    /**
     * 📌 FastAPI에 이미지 전송 및 결과 반환
     */
    public ImageResponseDto processImageSearch(MultipartFile file) {
        logger.info("Processing image search...");
        return fastImageClient.uploadImage(file);
    }
}
