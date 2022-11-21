package toyproject.demo.src.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toyproject.demo.src.board.model.DeleteBoardReq;
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

    //게시글 생성
    public int createBoard(PostBoardReq postBoardReq) {
        int boardIdx = boardDao.createBoard(postBoardReq);
        return boardIdx;
    }

    //게시글 삭제
    public String deleteBoard(int boardIdx) {
        boardDao.deleteBoard(boardIdx);
        return "삭제되었습니다.";
    }
}
