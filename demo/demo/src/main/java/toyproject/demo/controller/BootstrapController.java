package toyproject.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BootstrapController {
    @RequestMapping("/boardlist")
    public String boardList() {
        return "boardlist";
    }
    @RequestMapping("/ex07")
    public String ex07() {
        return "ex/ex07";
    }

}
