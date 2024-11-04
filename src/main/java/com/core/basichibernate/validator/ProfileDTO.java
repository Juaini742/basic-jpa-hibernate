package com.core.basichibernate.validator;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    @NotBlank(message = "firstlast name is required")
    private String firstName;

    @NotBlank(message = "lastname is required")
    private String lastName;

    @NotBlank(message = "phone is required")
    @Size(max = 13, min = 10, message = "phone must be between 10 and 13 characters")
    private String phone;

    @NotBlank(message = "address is required")
    @Size(max = 100, message = "address must be less than 100 characters")
    private String address;

    private Long userId;
    private UserDTO user;
}
