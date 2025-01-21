package com.project_merge.jigu_travel.api.board.service;


import com.project_merge.jigu_travel.api.board.dto.reponseDto.BoardResponseDto;
import com.project_merge.jigu_travel.api.board.dto.reponseDto.BoardUpdateRequestDto;
import com.project_merge.jigu_travel.api.board.dto.requestDto.BoardPostsRequestDto;
import com.project_merge.jigu_travel.api.board.dto.requestDto.BoardUpdateResponseDto;
import com.project_merge.jigu_travel.global.common.CommonResponseDto;
import org.springframework.data.domain.Page;

public interface BoardService {
    Page<BoardResponseDto> getBoardList(String accessToken, int page, int size);
    CommonResponseDto createBoard(String accessToken, BoardPostsRequestDto boardPostsRequestDto);
    BoardUpdateResponseDto modifyBoard(String accessToken, BoardUpdateRequestDto boardUpdateRequestDto);
    CommonResponseDto boardDeletion(String accessToken, Long boardId);
}
