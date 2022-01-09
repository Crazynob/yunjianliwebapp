package red.fuyun.model;


import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UserDo {

    private long userId;
    private String username;
    private String password;
    private String phone;
    private int sex;
    private int status;
    private List<Role> roles;
    private List<Resource> resources;

}
