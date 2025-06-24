package com.mavrommatis.ebookshop.ebookshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mavrommatis.ebookshop.ebookshop.dto.request.BookReviewsRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) used to expose book review information to API clients.
 * <p>
 * This class is primarily used in HTTP response bodies for endpoints
 * such as GET, POST, PUT, and DELETE operations related to book reviews.
 * </p>
 *
 * <p>Features:</p>
 * <ul>
 *   <li>Role-based field filtering for sensitive fields</li>
 *   <li>Exposes only client-safe information</li>
 *   <li>Populated exclusively server-side</li>
 * </ul>
 *
 * <p>
 * The fields {@code bookId} and {@code customerId} are dynamically omitted
 * from JSON responses if the authenticated user has the {@code ROLE_CUSTOMER} role.
 * </p>
 *
 * @see BookReviewsRequestDTO for the corresponding input DTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReviewsResponseDTO {

    /** Unique identifier of the review */
    private Integer reviewId;

    /** Identifier of the reviewed book (hidden for CUSTOMER role) */
    private Integer bookId;

    /** Identifier of the reviewer customer (hidden for CUSTOMER role) */
    private Integer customerId;

    /** Numeric rating (1-5) given in the review */
    private int rating;

    /** Optional comment left by the reviewer */
    private String comment;

    /** Timestamp of when the review was created */
    private LocalDateTime createdAt;

    /** Timestamp of the last update to the review */
    private LocalDateTime updatedAt;

    @JsonProperty("reviewId")
    public Integer getReviewId() {
        return reviewId;
    }

    @JsonProperty("rating")
    public int getRating() {
        return rating;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    @JsonProperty("createdAt")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("updatedAt")
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Gets the book ID if the current user is not a CUSTOMER.
     *
     * @return book ID or null if user has CUSTOMER role
     */
    @JsonProperty("bookId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getBookId() {
        return isCustomer() ? null : bookId;
    }

    /**
     * Gets the customer ID if the current user is not a CUSTOMER.
     *
     * @return customer ID or null if user has CUSTOMER role
     */
    @JsonProperty("customerId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getCustomerId() {
        return isCustomer() ? null : customerId;
    }

    // Setters (used for mapping and serialization)

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Checks if the current authenticated user has the CUSTOMER role.
     *
     * @return true if user has ROLE_CUSTOMER, otherwise false
     */
    private boolean isCustomer() {
        try {
            return SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_CUSTOMER"));
        } catch (Exception e) {
            return false;
        }
    }
}
