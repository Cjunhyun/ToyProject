package toyproject.demo.controller.board;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {

    @RequestMapping("/boardlist")
    public String boardList() {
        return "boardlist";
    }


    @RequestMapping("/boardsave")
    public String ex07() {
        return "boardsave";
    }
}
