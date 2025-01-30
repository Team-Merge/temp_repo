package com.project_merge.jigu_travel.api.board.service;

import com.project_merge.jigu_travel.api.auth.model.CustomUserDetails;
import com.project_merge.jigu_travel.api.board.dto.reponseDto.BoardResponseDto;
import com.project_merge.jigu_travel.api.board.dto.reponseDto.BoardUpdateRequestDto;
import com.project_merge.jigu_travel.api.board.dto.requestDto.BoardUpdateResponseDto;
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

    // 📌 게시글 목록 조회 (로그인 없이 가능)
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

    // 📌 게시글 작성
    @Override
    public CommonResponseDto createBoard(CustomUserDetails userDetails, BoardPostsRequestDto boardPostsRequestDto) {
        User user = userRepository.findByLoginIdAndDeletedFalse(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

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

    // 📌 게시글 수정
    @Override
    public BoardUpdateResponseDto modifyBoard(CustomUserDetails userDetails, BoardUpdateRequestDto boardUpdateRequestDto) {
        User user = userRepository.findByLoginIdAndDeletedFalse(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Board board = boardJpaRepository.findById(boardUpdateRequestDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!user.getUserId().equals(board.getUser().getUserId())) {
            throw new org.springframework.security.access.AccessDeniedException("게시글을 수정할 권한이 없습니다.");
        }

        board.setTitle(boardUpdateRequestDto.getTitle());
        board.setContent(boardUpdateRequestDto.getContent());
        boardJpaRepository.save(board);

        return BoardUpdateResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }

    // 📌 게시글 삭제
    @Override
    public CommonResponseDto boardDeletion(CustomUserDetails userDetails, Long boardId) {
        User user = userRepository.findByLoginIdAndDeletedFalse(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Board board = boardJpaRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!user.getUserId().equals(board.getUser().getUserId())) {
            throw new org.springframework.security.access.AccessDeniedException("게시글을 삭제할 권한이 없습니다.");
        }

        boardJpaRepository.delete(board);

        return CommonResponseDto.builder()
                .message("SUCCESS")
                .build();
    }

    // 📌 게시글 상세조회
    @Override
    public BoardResponseDto getBoardDetail(Long boardId) {
        Board board = boardJpaRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        return BoardResponseDto.builder()
                .boardId(board.getBoardId())
                .userId(board.getUser().getLoginId())
                .nickname(board.getUser().getNickname())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}
