package com.core.basichibernate.validator;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    @NotBlank(message = "content is required")
    private String content;

    private Long userId;

    private Long postId;
}
