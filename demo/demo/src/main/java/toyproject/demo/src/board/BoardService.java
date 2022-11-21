package toyproject.demo.src.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toyproject.demo.src.board.model.PostBoardReq;

@Service
public class BoardService {
    //======================================
    @Autowired
    private final BoardDao boardDao;

    public BoardService(BoardDao boardDao) {
        this.boardDao = boardDao;
    }
    //======================================

    public int createBoard(PostBoardReq postBoardReq) {
        int boardIdx = boardDao.createBoard(postBoardReq);
        return boardIdx;
    }
}
