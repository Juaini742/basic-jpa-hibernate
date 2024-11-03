package com.core.basichibernate.validator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty(message = "email is required")
    @Size(min = 2, message = "email must be at least 2 characters")
    @Email(message = "email is not valid")
    private String email;

    @NotEmpty(message = "name is required")
    @Size(min = 2, message = "name is required")
    private String name;

    @NotEmpty(message = "password is required")
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;

    private Long profileId;

    private List<Long> roleIds;

    private List<String> roleNames = new ArrayList<>();

}
