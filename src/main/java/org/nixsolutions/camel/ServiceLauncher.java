package org.nixsolutions.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
//@EnableSwagger2
public class ServiceLauncher {

	public static void main(String[] args) {
		SpringApplication.run(ServiceLauncher.class, args);
	}

//	@Bean
//	@Profile("!test")
//	public Docket productApi() {
//		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfo("My REST API", "Some custom description of API.", "API TOS", "Terms of service",
//				new Contact("John Doe", "www.example.com", "myeaddress@company.com"), "License of API",
//				"API license URL");
//	}
}
