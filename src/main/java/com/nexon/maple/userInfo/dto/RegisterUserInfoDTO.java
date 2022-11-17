package com.nexon.maple.userInfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@NoArgsConstructor
public class RegisterUserInfoDTO {

    @NotNull
    @Length(min = 2, max = 12)
    private String name;

    @NotNull
    @Length(min = 8, max = 20)
    private String password;

    @NotNull
    @Length(min = 8, max = 8)
    private String otpNumber;

    @NotNull
    @Positive
    private Long pageNumber;

    private List<String> terms;

}
