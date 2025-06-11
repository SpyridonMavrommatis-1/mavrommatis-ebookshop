package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for sending customer data back to the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDTO {

    /**
     * Unique identifier of the customer.
     */
    private int customerId;

    /**
     * Username chosen by the customer.
     */
    private String username;

    /**
     * Email address of the customer.
     */
    private String email;

    /**
     * Timestamp when the customer record was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last update to the customer record.
     */
    private LocalDateTime updatedAt;

    /**
     * Detailed profile information of the customer.
     */
    private CustomerDetailsDTO customerDetails;
}