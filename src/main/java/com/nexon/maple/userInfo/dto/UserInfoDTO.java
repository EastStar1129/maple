package com.nexon.maple.userInfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UserInfoDTO {

    @NotNull(message = "이름은 필수 값입니다.")
    @Length(min = 2, max = 12, message = "이름의 최소길이는 2자리에서 12자리입니다.")
    private String userName;
}
