package com.nexon.maple.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class WriteCommentDTO {

    @NotNull
    @Positive
    Long characterId;

    @NotNull
    @Length(min = 1, max = 4000)
    String comment;
}
