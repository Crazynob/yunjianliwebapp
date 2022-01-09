package red.fuyun.service.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import red.fuyun.Interceptor.feign.FeignRequestInterceptor;
import red.fuyun.model.OauthResult;
import red.fuyun.model.R;
import red.fuyun.model.feign.OauthBody;

@Service
@FeignClient(name="security",configuration={FeignRequestInterceptor.class})
public interface OauthService {



//    @PostMapping(value = "/oauth/token",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public Object getToken(@RequestBody OauthBody body);

    @PostMapping(value = "/oauth/token", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    R<OauthResult> getToken(@RequestBody OauthBody body);

    /**
     * 查询
     * @param id
     * @return
     */
    @GetMapping("product/provider/get/{id}")
    public void selectById(@PathVariable("id") Long id);
}
