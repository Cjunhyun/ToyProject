package toyproject.demo.controller.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toyproject.demo.controller.board.model.GetBoardRes;
import toyproject.demo.controller.board.model.GetBoardViewRes;

import java.util.List;

@Service
public class BoardProvider {
    //======================================
    @Autowired
    private final BoardDao boardDao;

    public BoardProvider(BoardDao boardDao) {
        this.boardDao = boardDao;
    }
    //======================================

    //모든 게시글 조회
    public List<GetBoardRes> getBoards() {
        List<GetBoardRes> getBoardRes = boardDao.getBoards();
        return getBoardRes;
    }

    //게시글 상세조회
    public GetBoardViewRes getBoardView(int boardIdx) {
        GetBoardViewRes getBoardViewRes = boardDao.getBoardView(boardIdx);
        return getBoardViewRes;

    }
}
