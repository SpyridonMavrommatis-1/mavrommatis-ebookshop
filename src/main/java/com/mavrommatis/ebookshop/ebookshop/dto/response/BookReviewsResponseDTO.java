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
 * DTO for exposing book review data to clients.
 *
 * <p>This DTO is used in HTTP responses and populated server-side,
 * hence <strong>does not require validation annotations</strong>.</p>
 *
 * <p>Any necessary validation (e.g., for rating, comment content) is expected
 * to be enforced on the corresponding request-side DTO {@link BookReviewsRequestDTO}.</p>
 *
 * <p>Additionally, this DTO uses logic to conditionally hide the fields
 * {@code bookId} and {@code customerId} for users with the role {@code CUSTOMER}.</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReviewsResponseDTO {

    private Integer reviewId;
    private Integer bookId;
    private Integer customerId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
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

    @JsonProperty("bookId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getBookId() {
        return isCustomer() ? null : bookId;
    }

    @JsonProperty("customerId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getCustomerId() {
        return isCustomer() ? null : customerId;
    }

    // Standard setters (για MapStruct)

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
