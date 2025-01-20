package com.project_merge.jigu_travel.board.service;

import com.project_merge.jigu_travel.board.dto.responseDto.BoardResponseDto;
import org.springframework.data.domain.Page;

public interface BoardService {
    Page<BoardResponseDto> getBoardList(int page, int size);
}
