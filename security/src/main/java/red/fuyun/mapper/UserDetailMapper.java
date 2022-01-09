package red.fuyun.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.fuyun.model.UserDo;

@Repository
public interface UserDetailMapper {
    UserDo findByUsername(@Param("username") String username);

    UserDo findUserDetailByUsername(@Param("username") String username);


}
