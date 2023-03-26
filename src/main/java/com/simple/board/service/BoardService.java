package com.simple.board.service;

import com.simple.board.entity.Board;
import com.simple.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception{

        // 저장할 경로
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        // 식별자 아이디, 랜덤으로 만들어줌
        UUID uuid = UUID.randomUUID();

        // 랜덤 아이디 + 원래 파일명 = 저장될 파일명
        String fileName = uuid + "_" + file.getOriginalFilename();

        // file 넣어줄 껍데기, 파일경로, 저장될 파일명
        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }


    // 게시글 리스트 처리
    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }
}
