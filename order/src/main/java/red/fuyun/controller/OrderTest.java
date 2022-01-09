package red.fuyun.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderTest {

    @RequestMapping("/order")
    public String order(){
        return "hello order";
    }
}
