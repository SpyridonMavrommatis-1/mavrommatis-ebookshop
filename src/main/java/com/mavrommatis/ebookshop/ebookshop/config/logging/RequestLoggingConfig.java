package com.mavrommatis.ebookshop.ebookshop.config.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Configuration class to enable logging of incoming HTTP requests.
 * Logs query strings and request payloads to assist in debugging REST interactions.
 */
@Configuration
public class RequestLoggingConfig {

    /**
     * Creates a filter that logs HTTP request details.
     *
     * @return CommonsRequestLoggingFilter with payload and query string logging enabled
     */
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);      // Log ?params
        filter.setIncludePayload(true);          // Log body content
        filter.setIncludeHeaders(true);         // Optional: can be true to see headers
        filter.setMaxPayloadLength(10000);       // Limit for body size in logs
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }
}