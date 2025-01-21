package com.project_merge.jigu_travel.api.board.service;

import com.project_merge.jigu_travel.api.board.dto.reponseDto.BoardResponseDto;
import com.project_merge.jigu_travel.api.board.entity.Board;
import com.project_merge.jigu_travel.api.board.repository.BoardJpaRepository;
import com.project_merge.jigu_travel.api.user.model.User;
import com.project_merge.jigu_travel.api.user.repository.UserRepository;
import com.project_merge.jigu_travel.api.board.dto.requestDto.BoardPostsRequestDto;
import com.project_merge.jigu_travel.api.user.service.UserService;
import com.project_merge.jigu_travel.global.common.CommonResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardJpaRepository boardJpaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public Page<BoardResponseDto> getBoardList(String accessToken, int page, int size) {

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

    @Override
    public CommonResponseDto createBoard(String accessToken, BoardPostsRequestDto boardPostsRequestDto) {
        User user = userRepository.findById(userService.getCurrentUserUUID()).orElseThrow(() -> new IllegalArgumentException());
        Board newBoard = Board.builder()
                .user(user)
                .title(boardPostsRequestDto.getTitle())
                .content(boardPostsRequestDto.getContent())
                .likes(0L)
                .build();

        boardJpaRepository.save(newBoard);

        return CommonResponseDto.builder()
                .message("SUCCESS")
                .build();
    }

    @Override
    public CommonResponseDto boardDeletion(String accessToken, Long boardId) {
        User user = userRepository.findById(userService.getCurrentUserUUID()).orElseThrow(() -> new IllegalArgumentException());
        Board board = boardJpaRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException());
        if(!user.getUserId().equals(board.getUser().getUserId())) {
            throw new IllegalArgumentException();
        }

        boardJpaRepository.delete(board);

        return CommonResponseDto.builder()
                .message("SUCCESS")
                .build();
    }
}
