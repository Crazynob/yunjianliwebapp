package red.fuyun.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import red.fuyun.mapper.UserMapper;
import red.fuyun.model.Menu;
import red.fuyun.model.PayLoad;
import red.fuyun.model.Resource;
import red.fuyun.model.User;
import red.fuyun.service.UserService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController extends BaseController {


    private final UserService userService;

    private final RedisTemplate<String,String> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;


    @GetMapping("info")
    public User info(@ModelAttribute("payload") PayLoad payload){
        String username = payload.getUserName();
        User info = userService.info(username);
        return info;
    }


    @GetMapping("menu")
    public List<Menu> menu(@ModelAttribute("payload") PayLoad payload){
        String username = payload.getUserName();

        return null;
    }

    @GetMapping("/logout")
    public void logout(@ModelAttribute("payload") PayLoad payload) throws ParseException {
        // JWT唯一标识
        String jti = payload.getJti();
        redisTemplate.boundValueOps(jti).set(jti);
    }


}
