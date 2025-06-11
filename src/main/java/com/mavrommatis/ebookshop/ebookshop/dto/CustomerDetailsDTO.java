package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object containing detailed profile information for a customer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDetailsDTO {

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
