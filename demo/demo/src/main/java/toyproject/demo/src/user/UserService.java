package toyproject.demo.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toyproject.demo.config.BaseException;
import toyproject.demo.config.BaseResponseStatus;
import toyproject.demo.config.secret.AES128;
import toyproject.demo.config.secret.Secret;
import toyproject.demo.src.user.model.DeleteUserReq;
import toyproject.demo.src.user.model.PatchUserReq;
import toyproject.demo.src.user.model.PostUserReq;
import toyproject.demo.src.user.model.PostUserRes;


@Service
public class UserService {

    //기본 요소 ============
    private final UserDao userDao;
    private final UserProvider userProvider;

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider) {
        this.userDao = userDao;
        this.userProvider = userProvider;
    }
    //==================

    //회원가입 [post]
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복 확인
        if (userProvider.checkEmail(postUserReq.getEmail()) == 1) {
            throw new BaseException(BaseResponseStatus.POST_USERS_EXISTS_EMAIL);
        }
        String pwd;
        try {
            //암호화 시켜 디비에 저장
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);
        } catch (Exception ignored) {
            //암호화 실패했을 경우 에러 발생
            throw new BaseException(BaseResponseStatus.PASSWORD_ENCRYPTION_ERROR);
        }

        try {
            int userIdx = userDao.createUser(postUserReq);
            return new PostUserRes(userIdx);
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

    //회원 정보 수정 [patch]
    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try {
            int result = userDao.modifyUserName(patchUserReq);
            if (result == 0) {
                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_USERNAME);
            }

        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    //회원 정보 삭제 [delete]
    public void deleteUser(DeleteUserReq deleteUserReq) throws BaseException {
        try {
            int result = userDao.deleteUser(deleteUserReq);
            if (result == 0) {
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_USER);
            }

        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }


}
