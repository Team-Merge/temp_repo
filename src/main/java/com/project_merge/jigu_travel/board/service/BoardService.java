package com.project_merge.jigu_travel.board.service;

import com.project_merge.jigu_travel.board.dto.requestDto.BoardPostsRequestDto;
import com.project_merge.jigu_travel.board.dto.responseDto.BoardResponseDto;
import com.project_merge.jigu_travel.global.common.CommonResponseDto;
import org.springframework.data.domain.Page;

public interface BoardService {
    Page<BoardResponseDto> getBoardList(int page, int size);
    CommonResponseDto createBoard(BoardPostsRequestDto boardPostsRequestDto);
}
