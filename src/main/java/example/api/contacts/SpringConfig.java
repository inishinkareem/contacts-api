package example.api.contacts;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SpringConfig {
  
  @Bean
  public Docket swaggerConfig() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("example.api.contacts.controller"))
        .build()
        .useDefaultResponseMessages(false)
        .apiInfo(apiDetails());
  }
  
  private ApiInfo apiDetails() {
    return new ApiInfo(
        "Contact Entry System", 
        "RESTful API endpoints to perform CRUD operations on the Contacts", 
        "1.0", 
        null, 
        null, 
        null, 
        null,
        Collections.emptyList());
  }

}
