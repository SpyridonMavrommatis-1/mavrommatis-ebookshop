package com.mavrommatis.ebookshop.ebookshop.dto.request;

import com.mavrommatis.ebookshop.ebookshop.dto.details.CustomerDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity;
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
