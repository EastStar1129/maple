package com.nexon.maple.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class WriteCommentDTO {

    @NotNull
    private String characterName;

    @NotNull
    @Length(min = 1, max = 4000)
    private String comment;
}
