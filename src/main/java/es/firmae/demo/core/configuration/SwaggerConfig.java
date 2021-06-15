package es.firmae.demo.core.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public static final Contact DEFAULT_CONTACT = new Contact()
            .name("Osmel Pérez Alzola")
            .email("osmelpa86@gmail.com")
            .url("https://www.linkedin.com/in/osmel-perez-alzola/");

    public static final Info DEFAULT_API_INFO = new Info()
            .title("Favorite user profiles")
            .description("Microservicio que comparte los perfiles de usuarios favoritos")
            .version("1.0")
            .contact(DEFAULT_CONTACT);

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(DEFAULT_API_INFO);
    }
}
