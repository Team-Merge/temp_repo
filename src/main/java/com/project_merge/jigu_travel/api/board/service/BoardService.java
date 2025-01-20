package com.project_merge.jigu_travel.api.board.service;

import com.project_merge.jigu_travel.api.board.dto.reponseDto.BoardResponseDto;
import org.springframework.data.domain.Page;

public interface BoardService {
    Page<BoardResponseDto> getBoardList(int page, int size);
}