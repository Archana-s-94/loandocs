package nz.archana.loandocs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI loanDocsOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Loan Document Generator API")
                .version("1.0")
                .description("Create document templates with placeholders and generate loan documents from JSON data."));
    }
}
