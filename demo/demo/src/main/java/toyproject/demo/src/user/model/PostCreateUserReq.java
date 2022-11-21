package toyproject.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCreateUserReq {
    String email;
    String user;
    String dogName;
    String password;
}
