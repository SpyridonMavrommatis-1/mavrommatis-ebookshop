package com.mavrommatis.ebookshop.ebookshop.dto.request;

import com.mavrommatis.ebookshop.ebookshop.dto.details.CustomerDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity;
import com.mavrommatis.ebookshop.ebookshop.validator.annotation.UniqueEmail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for managing customer data received from the client.
 *
 * <p>This DTO represents input data used to create or update a
 * {@link CustomerEntity CustomerEntity}.</p>
 *
 * <p>It encapsulates basic credentials and optional personal profile information provided by the client.</p>
 *
 * <ul>
 *   <li>{@code username} – chosen login name for the customer</li>
 *   <li>{@code password} – account password (usually stored encrypted server-side)</li>
 *   <li>{@code email} – contact email address</li>
 *   <li>{@link CustomerDetailsDTO} – optional additional profile metadata (e.g., phone, address)</li>
 * </ul>
 *
 * <p>This object is used in HTTP request bodies to manage customer-related operations.</p>
 *
 * @see CustomerEntity
 * @see CustomerDetailsDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDTO {

    /**
     * Username chosen by the customer.
     */
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    private String username;

    /**
     * Password for the customer's account.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    /**
     * Email address of the customer.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @UniqueEmail
    private String email;

    /**
     * Extended profile information for the customer (optional).
     */
    @Valid
    private CustomerDetailsDTO customerDetails;
}
