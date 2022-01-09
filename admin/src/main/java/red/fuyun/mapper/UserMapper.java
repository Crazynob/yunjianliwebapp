package red.fuyun.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.fuyun.model.Menu;
import red.fuyun.model.R;
import red.fuyun.model.Resource;
import red.fuyun.model.User;

import java.util.List;

@Repository
public interface UserMapper{

    User findUserByUsername(@Param("username") String username);

    List<Menu> findMenuByUser(@Param("user") User user);

    List<Resource> findResourceByUser(@Param("user") User user);


}
