package com.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EntityScan("com.wms.entity") // 扫描JPA实体类
@MapperScan("com.wms.mapper") // 扫描MyBatis-Plus Mapper接口
public class WmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WmsApplication.class, args);
    }

}
