package com.dev02.libraryproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
// !!! info parametresi, API'nin başlığını ve sürümünü içeren bir @Info nesnesi alır.
 //  security parametresi, API'ye erişmek için gereken güvenlik gereksinimlerini belirler.
@OpenAPIDefinition(info = @Info(title = "Library Project API", version = "1.0.0"),
                    security = @SecurityRequirement(name = "Bearer"))
// !!!  name parametresi, güvenlik şemasının adını belirtir. type parametresi, güvenlik şemasının
 //  türünü belirtir ve SecuritySchemeType.HTTP değeri kullanılarak HTTP güvenlik şemasını belirtir.
@SecurityScheme(name = "Bearer", type = SecuritySchemeType.HTTP, scheme = "Bearer")
public class OpenAPIConfig {

    // !!! http://localhost:8080/swagger-ui/index.html   --> SWAGGER API erisim adresi
}
