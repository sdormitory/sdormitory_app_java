package cn.sdormitory;

import cn.sdormitory.sys.dao.SysUserDao;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableConfigurationProperties
@MapperScan(basePackages = {"cn.sdormitory.*.dao"})
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
