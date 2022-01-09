package red.fuyun.controller;


import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.JWSObject;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import red.fuyun.model.PayLoad;

import java.text.ParseException;


public class BaseController {

    @ModelAttribute("payload")
    public PayLoad model(@RequestHeader("X-Token") String token) throws ParseException {
        JWSObject jwsObject = JWSObject.parse(token);
        PayLoad payload = buildPayLoad(jwsObject);
        return payload;

    }


    private PayLoad buildPayLoad(JWSObject jwsObject) {
        String payload = jwsObject.getPayload().toString();
        JSONObject payloadObject = JSONObject.parseObject(payload);
        PayLoad payLoad = payloadObject.toJavaObject(PayLoad.class);
        return payLoad;
    }

}
