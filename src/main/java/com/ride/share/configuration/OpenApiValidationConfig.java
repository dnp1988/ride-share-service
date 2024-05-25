package com.ride.share.configuration;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.springmvc.OpenApiValidationFilter;
import com.atlassian.oai.validator.springmvc.OpenApiValidationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * Configuration related to Open API support.
 */
@Configuration
public class OpenApiValidationConfig implements WebMvcConfigurer {

    /**
     * Location of the Open API configuration file
     */
    private static final String OPENAPI_YML = "openapi.yml";

    /**
     * Open API HTTP Interceptor used to validate requests and responses.
     */
    private final OpenApiValidationInterceptor validationInterceptor;

    /**
     * Creates a regular instance.
     * Creates and saves the {@link OpenApiValidationInterceptor} using the Open API configuration file.
     */
    @Autowired
    public OpenApiValidationConfig() {
        this.validationInterceptor = new OpenApiValidationInterceptor(
                OpenApiInteractionValidator
                        .createFor(OPENAPI_YML)
                        .build());
    }

    /**
     * Creates a bean for {@link OpenApiValidationFilter} containing configuration options for the interceptor.
     *
     * @return created bean
     */
    @Bean
    public Filter validationFilter() {
        return new OpenApiValidationFilter(true, false);
    }

    /**
     * Adds the Open API Validation Interceptor to the Applications registry.
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(validationInterceptor);
    }
}
