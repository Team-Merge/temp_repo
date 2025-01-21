package com.project_merge.jigu_travel.api.board.controller;


import com.project_merge.jigu_travel.api.board.dto.reponseDto.BoardResponseDto;
import com.project_merge.jigu_travel.api.board.dto.requestDto.BoardPostsRequestDto;
import com.project_merge.jigu_travel.api.board.service.BoardServiceImpl;
import com.project_merge.jigu_travel.global.common.BaseResponse;
import com.project_merge.jigu_travel.global.common.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardServiceImpl boardServiceImpl;

    /**
     *
     * @param page
     * @param size
     * @return ResponseEntity<BaseResponse<Page<BoardResponseDto>>>
     */

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<BaseResponse<Page<BoardResponseDto>>> getAllBoard(@RequestHeader("Authorization") String accessToken,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "5") int size) {
        Page<BoardResponseDto> boardPage = boardServiceImpl.getBoardList(accessToken, page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<Page<BoardResponseDto>>builder()
                        .code(HttpStatus.OK.value())
                        .data(boardPage)
                        .build());
    }

    /**
     *
     * @param boardPostsRequestDto
     * @return ResponseEntity<BaseResponse<BoardPostsResponseDto>>
     */
    @PostMapping("/posts")
    @ResponseBody
    public ResponseEntity<BaseResponse<CommonResponseDto>> addBoard(@RequestHeader("Authorization") String accessToken,
                                                                    @RequestBody BoardPostsRequestDto boardPostsRequestDto) {
        CommonResponseDto commonResponseDto = boardServiceImpl.createBoard(accessToken, boardPostsRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<CommonResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(commonResponseDto)
                        .build());
    }

    /**
     *
     * @param boardId
     * @return ResponseEntity<BaseResponse<CommonResponseDto>>
     */
    @DeleteMapping("/deletion")
    @ResponseBody
    public ResponseEntity<BaseResponse<CommonResponseDto>> deleteBoard(@RequestHeader("Authorization") String accessToken,
                                                                       @RequestParam Long boardId) {
        CommonResponseDto commonResponseDto = boardServiceImpl.boardDeletion(accessToken, boardId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<CommonResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(commonResponseDto)
                        .build());
    }
}
