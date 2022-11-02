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
    String name;

    @NotNull
    @Length(min = 8, max = 20)
    String password;

    @NotNull
    @Length(min = 8, max = 8)
    String otpNumber;

    @NotNull
    @Positive
    Long pageNumber;

    List<String> terms;

}
