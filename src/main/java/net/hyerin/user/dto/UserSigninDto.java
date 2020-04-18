package net.hyerin.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// lombok annotation 설명 -> https://hyerin6.github.io/2020-04-05/0405/
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserSigninDto {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "이메일 주소가 올바르지 않습니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min=6, max=12, message="6 자리 이상 12 자리 이하이어야 합니다.")
    private String password;

}
