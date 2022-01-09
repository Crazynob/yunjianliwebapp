package red.fuyun.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Role {

    @JsonIgnore
    private int roleId;
    private String role;
}
