package toyproject.demo.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import toyproject.demo.config.BaseException;
import toyproject.demo.config.BaseResponse;
import toyproject.demo.config.BaseResponseStatus;
import toyproject.demo.src.user.model.*;

import java.util.List;

import static toyproject.demo.config.secret.ValidationRegex.isRegexEmail;

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
    @RequestMapping("/users/sign-up")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {

        //이메일 값이 널인지 확인
        if (postUserReq.getEmail() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_EMPTY_EMAIL);
        }

        //이메일 정규표현 확인
        if (!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
        }

        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    //로그인 [post]
    @ResponseBody
    @PostMapping("/users/log-in")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {

        try {
            //형식적인 validation이 필요함
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //모든 회원 조회 [get]
    //해당 반려견이름을 같는 유저들의 정보 조회 [get]
    @ResponseBody
    @GetMapping("/users")
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String dogName) {
        try {
            if (dogName == null) {
                List<GetUserRes> getUserRes = userProvider.getUsers();
                return new BaseResponse<>(getUserRes);
            }
            //query에 dogName이 있을경우
            List<GetUserRes> getUserRes = userProvider.getUsersByDogName(dogName);
            return new BaseResponse<>(getUserRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //회원 1명 조회 [get]
    @ResponseBody
    @GetMapping("/users/{userIdx}")
    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx) {
        try {
            GetUserRes getUserRes = userProvider.getUser(userIdx);
            return new BaseResponse<>(getUserRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //회원 1명 정보 변경 [patch]
    @ResponseBody
    @PatchMapping("/users/{userIdx}")
    public BaseResponse<String> modifyUsername(@PathVariable("userIdx") int userIdx, @RequestBody PatchUserReq patchUserReq) {
        try {
            PatchUserReq patchUserReq2 = new PatchUserReq(userIdx, patchUserReq.getDogName());
            userService.modifyUserName(patchUserReq2);

            String result = "회원 정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //회원 삭제 [delete]
    @DeleteMapping("/users/{userIdx}")
    public BaseResponse<String> deleteUser(@PathVariable("userIdx") int userIdx) {
        try {
            DeleteUserReq deleteUserReq1 = new DeleteUserReq(userIdx);
            userService.deleteUser(deleteUserReq1);

            String result = "회원 정보가 삭제되었습니다.";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
