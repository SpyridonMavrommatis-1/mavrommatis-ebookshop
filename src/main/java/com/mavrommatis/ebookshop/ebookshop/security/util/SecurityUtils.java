package com.mavrommatis.ebookshop.ebookshop.security.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for accessing security-related information from the current {@code SecurityContext}.
 *
 * <p>This helper provides convenience methods to check user roles and extract authentication metadata
 * such as the currently logged-in username.</p>
 *
 * <p>Typical usage includes:</p>
 * <ul>
 *   <li>Checking if a user has a specific role (e.g. {@code ROLE_ADMIN})</li>
 *   <li>Conditionally exposing fields in DTOs based on role</li>
 *   <li>Retrieving the username of the authenticated principal</li>
 * </ul>
 *
 * <p>This class is commonly used in service, controller, or mapper layers for access control decisions.</p>
 */
public class SecurityUtils {

    /**
     * Checks whether the current authenticated user has the specified role.
     *
     * @param role the role to check (e.g. {@code "ROLE_ADMIN"})
     * @return {@code true} if the user has the role, otherwise {@code false}
     */
    public static boolean hasRole(String role) {
        try {
            return SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(r -> r.equals(role));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks whether the current authenticated user has the {@code ROLE_CUSTOMER}.
     *
     * @return {@code true} if the user is a customer; otherwise {@code false}
     */
    public static boolean isCustomer() {
        return hasRole("ROLE_CUSTOMER");
    }

    /**
     * Retrieves the username of the currently authenticated user.
     *
     * @return the username if available; otherwise {@code null}
     */
    public static String getCurrentUsername() {
        try {
            return SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();
        } catch (Exception e) {
            return null;
        }
    }
}
