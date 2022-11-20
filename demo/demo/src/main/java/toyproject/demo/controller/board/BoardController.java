package toyproject.demo.controller.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import toyproject.demo.controller.board.model.GetBoardRes;
import toyproject.demo.controller.board.model.GetBoardViewRes;
import toyproject.demo.controller.board.model.PostBoardReq;

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
        model.addAttribute("getBoardViewRes",getBoardViewRes);
        return "boardview";
    }
}
