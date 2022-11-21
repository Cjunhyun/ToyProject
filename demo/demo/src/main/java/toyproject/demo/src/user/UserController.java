package toyproject.demo.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.demo.src.user.model.PostCreateUserReq;
import toyproject.demo.src.user.model.PostCreateUserRes;

@RestController
public class UserController {
    //==========================
    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;

    public UserController(UserProvider userProvider, UserService userService) {
        this.userProvider = userProvider;
        this.userService = userService;
    }
    //==========================

    //프로필 등록 [post]
    @ResponseBody
    @PostMapping("/users/sign-up")
    public PostCreateUserRes createUser(PostCreateUserReq PostCreateUserReq) {
        PostCreateUserRes postCreateUserRes = new PostCreateUserRes;
        return postCreateUserRes;
    }
}
