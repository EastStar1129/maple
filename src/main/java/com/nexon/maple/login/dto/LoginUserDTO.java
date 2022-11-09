package com.nexon.maple.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class LoginUserDTO {

    @NotNull
    @Length(min = 2, max = 12)
    private String name;

    @NotNull
    @Length(min = 8, max = 20)
    private String password;
}
