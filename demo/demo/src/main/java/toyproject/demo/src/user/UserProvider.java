package toyproject.demo.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toyproject.demo.config.BaseException;
import toyproject.demo.config.BaseResponseStatus;
import toyproject.demo.config.secret.AES128;
import toyproject.demo.config.secret.Secret;
import toyproject.demo.src.user.model.GetUserRes;
import toyproject.demo.src.user.model.PostLoginReq;
import toyproject.demo.src.user.model.PostLoginRes;
import toyproject.demo.src.user.model.User;

import java.util.List;



@Service
public class UserProvider {
    //기본요소===============
    private final UserDao userDao;

    @Autowired
    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }
    //====================

    //로그인 패스워드 검사
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        User user = userDao.getPwd(postLoginReq);
        String password;

        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());

        } catch (Exception ignored) {
            throw new BaseException(BaseResponseStatus.PASSWORD_DECRYPTION_ERROR);
        }

        if (postLoginReq.getPassword().equals(password)) {
            int userIdx = userDao.getPwd(postLoginReq).getUserIdx();
            return new PostLoginRes(userIdx);

        } else {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }

    //해당 이메일이 이미 존재하는지 확인
    public int checkEmail(String email) throws BaseException {
        try {
            return userDao.checkEmail(email);

        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    //유저 조회(전체조회)
    public List<GetUserRes> getUsers() throws BaseException {
        try {
            List<GetUserRes> getUserRes = userDao.getUsers();
            return getUserRes;

        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    //유저 조회(반려견이름)
    public List<GetUserRes> getUsersByDogName(String dogName) throws BaseException {
        try {
            List<GetUserRes> getUserRes = userDao.getUsersByDogName(dogName);
            return getUserRes;

        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    //유저 조회(userIdx)
    public GetUserRes getUser(int userIdx) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(userIdx);
            return getUserRes;

        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
