package red.fuyun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"red.fuyun.mapper"})
public class SercurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SercurityApplication.class, args);
    }

}
