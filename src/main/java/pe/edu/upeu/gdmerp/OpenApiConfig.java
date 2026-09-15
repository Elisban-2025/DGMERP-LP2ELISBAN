package pe.edu.upeu.gdmerp;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GDMERP API")
                        .version("v1")
                        .description("Documentación OpenAPI para el backend del ERP Agro Peruvian Foods (GDMERP)"));
    }
}
