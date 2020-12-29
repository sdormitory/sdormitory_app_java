package cn.sdormitory.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Swagger2API文档的配置
 */
@Configuration
@EnableSwagger2
//@ComponentScan(basePackages = {"cn.sdormitory.controller"})
@EnableKnife4j
public class Swagger2AppConfig {
    @Bean
    public Docket createRestApi() {
        System.out.println("........................... createRestApi ............");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.sdormitory.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(this.securitySchemes());
    }

    private ApiInfo apiInfo() {
        System.out.println("........................... apiInfo ............");
        return new ApiInfoBuilder()
                .title("智慧宿管云平台APP")
                .description("智慧宿管云平台APP")
                .termsOfServiceUrl("http://localhost:9004/")
                .contact(new Contact("zhouyang","www.smartdormitoryapp.cn","912738803@qq.com"))
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        return Lists.newArrayList(new ApiKey("Authorization", "Authorization", "header"));
    }
}