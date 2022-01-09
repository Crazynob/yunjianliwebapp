package red.fuyun.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.List;

@Data
public class Menu {

    @JsonIgnore
    private int menuId;

    private String menuName;

    @JsonIgnore
    private int menuParent;

    private String menuPath;

    @JsonIgnore
    private int menulevel;

    private List<Menu> children;
}
