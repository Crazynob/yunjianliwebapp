package red.fuyun.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Resource implements GrantedAuthority {
    private long resourceId;
    private String resourceName;
    private String path;
    private String method;
    private int type;

    @Override
    public String getAuthority() {
        return resourceName+":"+path+":"+type;

    }
}
