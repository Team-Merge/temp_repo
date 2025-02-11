package com.project_merge.jigu_travel.api.board.controller;

import com.project_merge.jigu_travel.api.auth.model.CustomUserDetails;
import com.project_merge.jigu_travel.api.board.dto.reponseDto.BoardResponseDto;
import com.project_merge.jigu_travel.api.board.dto.requestDto.BoardUpdateResponseDto;
import com.project_merge.jigu_travel.api.board.entity.Attachment;
import com.project_merge.jigu_travel.api.board.repository.AttachmentJpaRepository;
import com.project_merge.jigu_travel.api.board.service.BoardServiceImpl;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import com.project_merge.jigu_travel.global.common.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardServiceImpl boardServiceImpl;

    private final AttachmentJpaRepository attachmentJpaRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 게시글 목록 조회
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<BaseResponse<Map<String, Object>>> getAllBoard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Page<BoardResponseDto> boardPage = boardServiceImpl.getBoardList(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", boardPage.getNumber());
        response.put("totalPages", boardPage.getTotalPages());
        response.put("totalItems", boardPage.getTotalElements());
        response.put("size", boardPage.getSize());
        response.put("posts", boardPage.getContent());

        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<Map<String, Object>>builder()
                        .code(HttpStatus.OK.value())
                        .data(response)
                        .build());
    }

    // 게시글 작성
    @PostMapping(value = "/posts", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<BaseResponse<CommonResponseDto>> addBoard(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("inquiryType") String inquiryType,
            @RequestParam(value = "files", required = false) List<MultipartFile> file) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    BaseResponse.<CommonResponseDto>builder()
                            .code(HttpStatus.UNAUTHORIZED.value())
                            .message("로그인이 필요합니다.")
                            .build()
            );
        }

        CommonResponseDto commonResponseDto = boardServiceImpl.createBoard(userDetails, title, content, inquiryType, file);
        return ResponseEntity.ok(BaseResponse.<CommonResponseDto>builder()
                .code(HttpStatus.OK.value())
                .data(commonResponseDto)
                .build());
    }

    // 게시글 수정
    @PatchMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<BaseResponse<BoardUpdateResponseDto>> updateBoard(
            @AuthenticationPrincipal CustomUserDetails userDetails, // ✅ 토큰 대신 SecurityContext 사용
            @RequestParam("boardId") Long boardId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("inquiryType") String inquiryType,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @RequestParam(value = "removedFiles", required = false) List<String> removedFiles) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    BaseResponse.<BoardUpdateResponseDto>builder()
                            .code(HttpStatus.UNAUTHORIZED.value())
                            .message("로그인이 필요합니다.")
                            .build()
            );
        }

        BoardUpdateResponseDto boardUpdateResponseDto = boardServiceImpl.modifyBoard(userDetails, boardId, title, content, inquiryType, files, removedFiles);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<BoardUpdateResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(boardUpdateResponseDto)
                        .build());
    }

    // 게시글 삭제
    @DeleteMapping("/deletion")
    @ResponseBody
    public ResponseEntity<BaseResponse<CommonResponseDto>> deleteBoard(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam Long boardId) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    BaseResponse.<CommonResponseDto>builder()
                            .code(HttpStatus.UNAUTHORIZED.value())
                            .message("로그인이 필요합니다.")
                            .build()
            );
        }

        CommonResponseDto commonResponseDto = boardServiceImpl.boardDeletion(userDetails, boardId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<CommonResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(commonResponseDto)
                        .build());
    }

    // 게시글 상세 조회
    @GetMapping("/detail/{boardId}")
    @ResponseBody
    public ResponseEntity<BaseResponse<BoardResponseDto>> getBoardDetail(@PathVariable Long boardId) {
        BoardResponseDto boardDetail = boardServiceImpl.getBoardDetail(boardId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<BoardResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(boardDetail)
                        .build());
    }


    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<BaseResponse<Void>> handleAccessDeniedException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(BaseResponse.<Void>builder()
                        .code(HttpStatus.FORBIDDEN.value())
                        .message(e.getMessage())
                        .build());
    }

    // 첨부파일 다운로드
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
        try {
            // 데이터베이스에서 파일 존재 여부 확인
            Attachment attachment = attachmentJpaRepository.findByFileName(fileName)
                    .orElseThrow(() -> new IllegalArgumentException("해당 파일을 찾을 수 없습니다."));

            // 저장된 파일의 절대 경로 설정
            Path filePath = Paths.get(uploadDir).resolve(attachment.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String encodedFileName = URLEncoder.encode(attachment.getFileName(), StandardCharsets.UTF_8);
            // 파일 다운로드를 위한 헤더 설정
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
