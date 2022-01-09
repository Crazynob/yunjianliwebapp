package red.fuyun.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.fuyun.model.FromBody;
import red.fuyun.model.OauthResult;
import red.fuyun.model.R;
import red.fuyun.model.feign.OauthBody;
import red.fuyun.service.feign.OauthService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginControler {

    private final OauthService oauthService;

    @PostMapping("/login")
    public Map login(@RequestBody FromBody body){
        System.out.println(body);
        OauthBody oauthBody = new OauthBody(body.getUsername(),body.getPassword(),"password");
//        OauthResult token = oauthService.getToken(oauthBody);
        R<OauthResult> result = oauthService.getToken(oauthBody);
        OauthResult oauthResult = result.getData();
        HashMap<String, String> map = new HashMap<>();
        map.put("token",oauthResult.getAccess_token());
        return map;
    }
}
