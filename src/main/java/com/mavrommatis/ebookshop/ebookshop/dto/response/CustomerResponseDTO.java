package com.mavrommatis.ebookshop.ebookshop.dto.response;

import com.mavrommatis.ebookshop.ebookshop.dto.details.CustomerDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for sending customer information to clients.
 *
 * <p>This DTO provides a flattened and client-friendly representation of a
 * {@link CustomerEntity}
 * enriched optionally with nested profile data.</p>
 *
 * <p>It encapsulates essential user account information such as:</p>
 * <ul>
 *   <li>{@code username} – the login identity of the customer</li>
 *   <li>{@code email} – the contact address associated with the customer</li>
 *   <li>{@link CustomerDetailsDTO} – optional detailed profile info like full name, address, etc.</li>
 * </ul>
 *
 * <p>The timestamps {@code createdAt} and {@code updatedAt} reflect the lifecycle of the customer record.</p>
 *
 * <p><strong>Note:</strong> This class does <em>not</em> use validation annotations
 * such as {@code @NotBlank} or {@code @Email} because it is populated
 * exclusively by the server for response purposes.</p>
 *
 * <p>Validation constraints are only relevant to input-bound classes (e.g. request DTOs),
 * where user-provided data must be checked before persistence. Here, data is
 * serialized for outbound use, and thus considered trustworthy.</p>
 *
 * @see CustomerEntity
 * @see CustomerDetailsDTO
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