package red.fuyun.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class User {
    @JsonIgnore
    private int userId;


    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String phone;

    @JsonIgnore
    private int sex;

    @JsonIgnore
    private int status;

    private List<Role> role;

    private List<Menu> menu;

    private List<Resource> resource;


}
