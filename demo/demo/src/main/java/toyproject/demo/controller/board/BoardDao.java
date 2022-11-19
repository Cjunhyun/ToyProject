package toyproject.demo.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import toyproject.demo.controller.board.model.GetBoardRes;
import toyproject.demo.controller.board.model.PostBoardReq;
import toyproject.demo.controller.board.model.PostBoardRes;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BoardDao {

    //======================================
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    //======================================


    //db에 모든 게시글 조회
    public List<GetBoardRes> getBoards() {
        String getUsersQuery = "select * from Board";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetBoardRes(
                        rs.getInt("boardIdx"),
                        rs.getString("title"),
                        rs.getString("user"))
        );
    }

    //게시글 생성
    public int createBoard(PostBoardReq postBoardReq) {
        String createBoardQuery = "insert into Board (title, user, content,password) VALUES (?,?,?,?)";
        Object[] createBoardParams = new Object[]{postBoardReq.getTitle(),postBoardReq.getUser(),postBoardReq.getContent(),postBoardReq.getPassword()};
        this.jdbcTemplate.update(createBoardQuery,createBoardParams);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }



}
