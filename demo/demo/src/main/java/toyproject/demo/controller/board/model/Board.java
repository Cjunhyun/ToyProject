package toyproject.demo.controller.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Board {
    int boardIdx;
    String title;
    String user;
    String content;
    String password;
}
