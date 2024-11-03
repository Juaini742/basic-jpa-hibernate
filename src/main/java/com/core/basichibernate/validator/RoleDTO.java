package com.core.basichibernate.validator;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    @NotEmpty(message = "name is required")
    private String name;

    private Set<Long> userIds;
}
