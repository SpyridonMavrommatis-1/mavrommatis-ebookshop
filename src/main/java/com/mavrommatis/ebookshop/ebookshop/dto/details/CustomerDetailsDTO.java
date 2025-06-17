package com.mavrommatis.ebookshop.ebookshop.dto.details;

import com.mavrommatis.ebookshop.ebookshop.entity.details.CustomerDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing extended profile information of a customer.
 *
 * <p>This DTO is used both in incoming {@code request} payloads and outgoing {@code response} bodies,</p>
 * <p>since the structure is symmetrical and contains no server-managed fields (e.g., timestamps, IDs).</p>
 *
 * <p>It is associated with the
 * {@link CustomerDetailsEntity CustomerDetailsEntity}
 * and mapped via a one-to-one relationship with
 * {@link CustomerEntity CustomerEntity}.</p>
 *
 * @see CustomerDetailsEntity
 * @see CustomerEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDetailsDTO {


    /**
     * Unique identifier of the customer (foreign key reference).
     */
    private Integer customerId;

    /**
     * Customer's first name.
     */
    private String firstName;

    /**
     * Customer's last name.
     */
    private String lastName;

    /**
     * Customer's mailing address.
     */
    private String address;

    /**
     * Customer's contact phone number.
     */
    private String phone;
}
