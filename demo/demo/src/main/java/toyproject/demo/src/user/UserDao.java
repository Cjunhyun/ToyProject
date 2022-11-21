package toyproject.demo.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import toyproject.demo.src.user.model.*;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    //기본 요소 ===========
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    //===================

    //회원가입
    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into User (email, user, dogName, password) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getUser(), postUserReq.getDogName(),postUserReq.getPassword()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    //이메일 확인
    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;

        return this.jdbcTemplate.queryForObject(checkEmailQuery, int.class, checkEmailParams);
    }

    //회원정보 변경
    public int modifyUserName(PatchUserReq patchUserReq) {
        String modifyUserNameQuery = "update User set dogName = ? where userIdx = ?";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getDogName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
    }

    //회원정보 삭제
    public int deleteUser(DeleteUserReq deleteUserReq) {
        String deleteUserQuery = "delete from User where userIdx = ?";
        int deleteUserParams = deleteUserReq.getUserIdx();

        return this.jdbcTemplate.update(deleteUserQuery, deleteUserParams);
    }

    //로그인: 해당 email에 해당되는 user의 암호화된 비밀번호 값을 가져온다.
    public User getPwd(PostLoginReq postLoginReq) {
        String getPwdQuery = "select userIdx,email,user,dogName,password from User where email = ?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("user"),
                        rs.getString("dogName"),
                        rs.getString("password")
                ),
                getPwdParams);
    }

    //User 테이블 전체 유저들의 정보 조회
    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select * from User";

        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("user"),
                        rs.getString("dogName"),
                        rs.getString("password")
                ));
    }

    //dogName을 통한 해당 회원 조회
    public List<GetUserRes> getUsersByDogName(String dogName) {
        String getUsersByDogNameQuery = "select * from User where dogName = ?";
        String getUsersByDogNameParams = dogName;

        return this.jdbcTemplate.query(getUsersByDogNameQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("user"),
                        rs.getString("dogName"),
                        rs.getString("password")),
                getUsersByDogNameParams);
    }

    //해당 userIdx를 갖는 유저조회
    public GetUserRes getUser(int userIdx) {
        String getUserQuery = "select * from User where userIdx = ?";
        int getUserParams = userIdx;

        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("user"),
                        rs.getString("dogName"),
                        rs.getString("password")),
                getUserParams);
    }
}

