package com.zeilvoortgang;

import org.springframework.boot.SpringApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.resource.PathResourceResolver;

import org.springframework.lang.NonNull;
import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springdoc.core.GroupedOpenApi;

@ComponentScan("com.zeilvoortgang")
@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties({ DataSourceProperties.class })
public class ZeilvoortgangApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeilvoortgangApplication.class, args);
	}

	@Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        return dataSource;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Zeil voortgang API")
                        .version("1.0")
                        .description("API for managing parties")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@zeilvoortgang.com")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("zeilvoortgang")
                .pathsToMatch("/**")
                .build();
    }

    // http://localhost:8080/swagger-ui.html
    @Configuration
    class SwaggerUiWebMvcConfigurer implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/swagger-ui/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/springdoc-swagger-ui/")
                    .setCachePeriod(0)
                    .resourceChain(true)
                    .addResolver(new PathResourceResolver() {
                        @Override
                        protected Resource getResource(@SuppressWarnings("null") String resourcePath,
                                @SuppressWarnings("null") Resource location) throws IOException {
                            return location.exists() && location.isReadable() ? location
                                    : new ClassPathResource(
                                            "/META-INF/resources/webjars/springdoc-swagger-ui/index.html");
                        }
                    });
        }
    }
}
