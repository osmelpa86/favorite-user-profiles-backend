package es.firmae.demo.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Value("${server.origins}")
    private String origins;

    /**
     * Specify which domains are authorized and which are not, and what they are authorized for.,
     *
     * @return WebMvcConfigurer configuration for project
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") /*	/ENTITY_API/**	*/
//                        .allowedOrigins(String.valueOf(Collections.singletonList("*"))) /*	ALL(*) or http://localhost:port	 (8083 or other port) */
                        .allowedOrigins(origins)
                        .allowedMethods("PUT", "DELETE", "GET", "POST", "PATCH" /*, other methods*/)
                        .allowedHeaders("*")
                        .allowCredentials(true)
                /*.maxAge(3600);*/             /* You can define maximum number of years*/
                ;
            }
        };
    }
}
