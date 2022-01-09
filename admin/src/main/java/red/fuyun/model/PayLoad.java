package red.fuyun.model;

import lombok.Data;

import java.util.List;

@Data
public class PayLoad {
// {"aud":["all_login"],"user_name":"fuyun","scope":["all"],"exp":1641193438,"authorities":["p1"],"jti":"c289f8cb-d5c2-4e08-bfc4-debd2e29066a","client_id":"login"}

    private  List<String> aud;

    private String userName;

    private String clientId;

    private long exp;

    private String jti;

//    private String grantType;

//    private String ATI = "ati";

    private List<String> scope;

    private List<String> authorities;

}
