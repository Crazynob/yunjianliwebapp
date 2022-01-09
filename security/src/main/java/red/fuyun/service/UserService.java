package red.fuyun.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import red.fuyun.mapper.UserDetailMapper;

import red.fuyun.model.UserDo;

import java.util.Objects;


@Service

public class UserService implements UserDetailsService {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailMapper userDetailMapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        UserDo userDo = userDetailMapper.findByUsername(username);
        UserDo userDo = userDetailMapper.findUserDetailByUsername(username);
        System.out.println("userDo:"+userDo);
        if (Objects.isNull(userDo)){
            throw new UsernameNotFoundException("此用户不存在!");
        }
        UserDetails userDetails = buildUser(userDo);

        return userDetails;

    }

    private UserDetails buildUser(UserDo userDo){

        User.UserBuilder builder = User.builder();
        builder.username(userDo.getUsername());
        builder.password(passwordEncoder.encode(userDo.getPassword()));
        builder.authorities(userDo.getResources());
//        builder.resources(userDo.getResources());
        builder.roles("DEFAULT_USER");
        int status = userDo.getStatus();
        UserDetails details = builder.build();

        return details;
    }
}
