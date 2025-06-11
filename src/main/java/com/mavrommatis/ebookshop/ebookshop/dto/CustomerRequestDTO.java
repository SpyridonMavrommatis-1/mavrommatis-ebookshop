package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object used when creating or updating a customer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDTO {

    /**
     * Username chosen by the customer.
     */
    private String username;

    /**
     * Password for the customer's account.
     */
    private String password;

    /**
     * Email address of the customer.
     */
    private String email;

    /**
     * Extended profile information for the customer (optional).
     */
    private CustomerDetailsDTO customerDetails;
}
