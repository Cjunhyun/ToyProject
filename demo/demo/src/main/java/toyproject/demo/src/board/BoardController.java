package toyproject.demo.src.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproject.demo.src.board.model.GetBoardRes;
import toyproject.demo.src.board.model.GetBoardViewRes;
import toyproject.demo.src.board.model.PostBoardReq;

import java.util.List;

@Controller
public class BoardController {

    //======================================
    @Autowired
    private final BoardProvider boardProvider;
    @Autowired
    private final BoardService boardService;

    public BoardController(BoardProvider boardProvider, BoardService boardService) {
        this.boardProvider = boardProvider;
        this.boardService = boardService;
    }
    //======================================

    //전체 게시글 조회 화면
    @RequestMapping("/boardlist")
    public String boardList(Model model) {
        List<GetBoardRes> getBoardRes = boardProvider.getBoards();
        model.addAttribute("getBoardRes", getBoardRes);
        return "boardlist";
    }

    //게시글 작성 화면
    @RequestMapping("/boardsave")
    public String boarSavePage() {
        return "boardsave";
    }

    //게시글 작성
    @PostMapping("/board")
    public String boardWrite(PostBoardReq postBoardReq, Model model) {
        boardService.createBoard(postBoardReq);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "boardlist");
        return "boardlist";

    }

    //게시글 상세페이지
    @GetMapping("/board/{boardIdx}")
    public String boardView(@PathVariable("boardIdx") int boarIdx, Model model) {
        GetBoardViewRes getBoardViewRes = boardProvider.getBoardView(boarIdx);
        model.addAttribute("getBoardViewRes", getBoardViewRes);
        return "boardview";
    }

    //게시글 삭제
    @GetMapping("/board/delete")
    public String boardDeleteCheck() {
        return "boardcheck";
    }

    @DeleteMapping("/board/{boardIdx}")
    public String boardDelete(@PathVariable("boardIdx") int boardIdx, Model model) {
        boardService.deleteBoard(boardIdx);
        return "index";
    }
}
