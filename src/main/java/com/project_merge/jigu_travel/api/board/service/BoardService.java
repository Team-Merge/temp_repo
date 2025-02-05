package com.project_merge.jigu_travel.api.board.service;


import com.project_merge.jigu_travel.api.auth.model.CustomUserDetails;
import com.project_merge.jigu_travel.api.board.dto.reponseDto.BoardResponseDto;
import com.project_merge.jigu_travel.api.board.dto.reponseDto.BoardUpdateRequestDto;
import com.project_merge.jigu_travel.api.board.dto.requestDto.BoardPostsRequestDto;
import com.project_merge.jigu_travel.api.board.dto.requestDto.BoardUpdateResponseDto;
import com.project_merge.jigu_travel.global.common.CommonResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    Page<BoardResponseDto> getBoardList(int page, int size);
    CommonResponseDto createBoard(CustomUserDetails userDetails, String title, String content, List<MultipartFile> files);  // ✅ 수정
    BoardUpdateResponseDto modifyBoard(CustomUserDetails userDetails, Long boardId, String title, String content, List<MultipartFile> files, List<String> removedFileNames); // ✅ 수정
    CommonResponseDto boardDeletion(CustomUserDetails userDetails, Long boardId); // ✅ 수정
    BoardResponseDto getBoardDetail(Long boardId);
}
