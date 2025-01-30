package com.project_merge.jigu_travel.api.board.controller;

import com.project_merge.jigu_travel.api.auth.model.CustomUserDetails;
import com.project_merge.jigu_travel.api.board.dto.reponseDto.BoardResponseDto;
import com.project_merge.jigu_travel.api.board.dto.reponseDto.BoardUpdateRequestDto;
import com.project_merge.jigu_travel.api.board.dto.requestDto.BoardPostsRequestDto;
import com.project_merge.jigu_travel.api.board.dto.requestDto.BoardUpdateResponseDto;
import com.project_merge.jigu_travel.api.board.service.BoardServiceImpl;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import com.project_merge.jigu_travel.global.common.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardServiceImpl boardServiceImpl;

    /**
     * 📌 게시글 목록 조회 (인증 없이 접근 가능)
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<BaseResponse<Page<BoardResponseDto>>> getAllBoard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("[DEBUG] SecurityContext Authentication: " + authentication);

        Page<BoardResponseDto> boardPage = boardServiceImpl.getBoardList(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<Page<BoardResponseDto>>builder()
                        .code(HttpStatus.OK.value())
                        .data(boardPage)
                        .build());
    }

    /**
     * 📌 게시글 작성 (로그인 필요)
     */
    @PostMapping("/posts")
    @ResponseBody
    public ResponseEntity<BaseResponse<CommonResponseDto>> addBoard(
            @AuthenticationPrincipal CustomUserDetails userDetails, // ✅ SecurityContext에서 사용자 정보 가져오기
            @RequestBody BoardPostsRequestDto boardPostsRequestDto) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    BaseResponse.<CommonResponseDto>builder()
                            .code(HttpStatus.UNAUTHORIZED.value())
                            .message("로그인이 필요합니다.")
                            .build()
            );
        }

        CommonResponseDto commonResponseDto = boardServiceImpl.createBoard(userDetails, boardPostsRequestDto);
        return ResponseEntity.ok(BaseResponse.<CommonResponseDto>builder()
                .code(HttpStatus.OK.value())
                .data(commonResponseDto)
                .build());
    }

    /**
     * 📌 게시글 수정 (로그인 필요)
     */
    @PatchMapping("/update")
    @ResponseBody
    public ResponseEntity<BaseResponse<BoardUpdateResponseDto>> updateBoard(
            @AuthenticationPrincipal CustomUserDetails userDetails, // ✅ 토큰 대신 SecurityContext 사용
            @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    BaseResponse.<BoardUpdateResponseDto>builder()
                            .code(HttpStatus.UNAUTHORIZED.value())
                            .message("로그인이 필요합니다.")
                            .build()
            );
        }

        BoardUpdateResponseDto boardUpdateResponseDto = boardServiceImpl.modifyBoard(userDetails, boardUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<BoardUpdateResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(boardUpdateResponseDto)
                        .build());
    }

    /**
     * 📌 게시글 삭제 (로그인 필요)
     */
    @DeleteMapping("/deletion")
    @ResponseBody
    public ResponseEntity<BaseResponse<CommonResponseDto>> deleteBoard(
            @AuthenticationPrincipal CustomUserDetails userDetails, // ✅ SecurityContext에서 사용자 정보 가져오기
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

    /**
     * 📌 게시글 상세 조회 (인증 없이 접근 가능)
     */
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

}
