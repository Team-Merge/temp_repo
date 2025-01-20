package com.project_merge.jigu_travel.board.service;

import com.project_merge.jigu_travel.board.dto.responseDto.BoardResponseDto;
import com.project_merge.jigu_travel.board.entity.Board;
import com.project_merge.jigu_travel.board.repository.BoardJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardJpaRepository boardJpaRepository;

    public Page<BoardResponseDto> getBoardList(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("boardId").descending());
        Page<Board> boardPage = boardJpaRepository.findAll(pageRequest);

        return boardPage.map(board -> BoardResponseDto.builder()
                .boardId(board.getBoardId())
                .userId(board.getUser().getLoginId())
                .nickname(board.getUser().getNickname())
                .title(board.getTitle())
                .content(board.getContent())
                .likes(board.getLikes())
                .build());
    }
}
