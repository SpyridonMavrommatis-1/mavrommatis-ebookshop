package com.mavrommatis.ebookshop.ebookshop.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Configuration class for enabling structured HTTP request logging across the application.
 *
 * <p>This class registers a {@link CommonsRequestLoggingFilter} bean that intercepts all incoming HTTP
 * requests and logs useful metadata for debugging and auditing purposes.</p>
 *
 * <p>The filter captures:</p>
 * <ul>
 *   <li>Query parameters (e.g., ?id=123)</li>
 *   <li>Request body content (with configurable max size)</li>
 *   <li>Headers (optional)</li>
 * </ul>
 *
 * <p>Typical usage includes identifying malformed payloads, tracking client behavior,
 * and improving observability in production environments.</p>
 */
@Configuration
public class RequestLoggingConfig {

    /**
     * Registers a filter that logs inbound HTTP request details such as URI,
     * headers, query parameters, and payload content.
     *
     * <p>Configured to include headers, body (up to 10KB), and query string.
     * Output is prefixed with {@code "REQUEST DATA : "} for easy log scanning.</p>
     *
     * @return a configured {@link CommonsRequestLoggingFilter} instance
     */
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);        // Enables logging of ?param=value
        filter.setIncludePayload(true);            // Enables logging of body content
        filter.setIncludeHeaders(true);            // Optional: log request headers
        filter.setMaxPayloadLength(10000);         // Limit request body logging to 10KB
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }
}
