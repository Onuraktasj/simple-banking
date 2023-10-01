package onuraktas.simplebanking.spring.config;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {


    private static final String CONTROLLER_PATH = "onuraktas.simplebanking.controller";


    @Bean
    public OpenAPI swaggerDocumentation() {
        Contact contact = new Contact();
        contact.setName("Onur Aktas");
        return new OpenAPI()
                .info(new Info().title("Swagger UI")
                        .description("Simple Banking Backend Swagger UI")
                        .version("1.0.0")
                        .contact(contact)
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Simple Banking Backend Swagger UI"));
    }


    @Bean
    public GroupedOpenApi allApi() {
        return GroupedOpenApi.builder()
                .group("all-api")
                .packagesToScan(CONTROLLER_PATH)
                .build();
    }


    @Bean
    public GroupedOpenApi eventApi() {
        return GroupedOpenApi.builder()
                .group("event-api")
                .packagesToScan(CONTROLLER_PATH)
                .pathsToMatch("/account/**")
                .build();
    }
}