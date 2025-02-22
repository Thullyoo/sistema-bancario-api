package br.thullyoo.sistema_bancario.config.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema bancario API")
                        .version("1.0")
                        .description("API que simula sistema de registro de transações entre usuários")
                        .contact(new Contact()
                                .email("thullyocontact@gmail.com")
                                .name("Thúllyo Barcellos")
                        )
                );
    }

}
