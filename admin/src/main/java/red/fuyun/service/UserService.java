package red.fuyun.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import red.fuyun.mapper.UserMapper;
import red.fuyun.model.Menu;
import red.fuyun.model.Resource;
import red.fuyun.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {


    private final UserMapper userMapper;

    public User info(String username){
        User user = userMapper.findUserByUsername(username);
        List<Menu> menuList = userMapper.findMenuByUser(user);
        List<Resource> resourceList = userMapper.findResourceByUser(user);

        List<Menu> oneLevelMenuLsit = new ArrayList<>();
        List<Menu> twoLevelMenuLsit = new ArrayList<>();
        HashMap<Integer, List<Menu>> twoLevelMenuMap = new HashMap<>();
        HashMap<Integer, List<Menu>> threeLevelMenuMap = new HashMap<>();



        menuList.stream().forEach(menu -> {
            int menulevel = menu.getMenulevel();
            if (menulevel == 3){
                List<Menu> menus = threeLevelMenuMap.get(menu.getMenuParent());
                if (Objects.isNull(menus)){
                    menus = new ArrayList<>();
                    menus.add(menu);

                }else {
                    menus.add(menu);
                }
                threeLevelMenuMap.put(menu.getMenuParent(),menus);

            }
            if (menulevel == 2){
                twoLevelMenuLsit.add(menu);
                List<Menu> menus = twoLevelMenuMap.get(menu.getMenuParent());
                if (Objects.isNull(menus)){
                    menus = new ArrayList<>();
                    menus.add(menu);
                }else {
                    menus.add(menu);

                }

                twoLevelMenuMap.put(menu.getMenuParent(),menus);
            }
            if (menulevel ==1){
                oneLevelMenuLsit.add(menu);
            }
        });

        oneLevelMenuLsit.stream().forEach(menu -> {
            int menuId = menu.getMenuId();
            List<Menu> children = twoLevelMenuMap.get(menuId);
            menu.setChildren(children);
        });

        twoLevelMenuLsit.stream().forEach(menu -> {
            int menuId = menu.getMenuId();
            List<Menu> children = threeLevelMenuMap.get(menuId);
            menu.setChildren(children);
        });

        user.setMenu(oneLevelMenuLsit);
        user.setResource(resourceList);

        return user;
    }
}
