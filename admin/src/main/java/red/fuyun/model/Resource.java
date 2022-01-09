package red.fuyun.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Resource {
    @JsonIgnore
    private long resourceId;
    private String resourceName;
    private String path;

    @JsonIgnore
    private String method;

    @JsonIgnore
    private int type;

}
