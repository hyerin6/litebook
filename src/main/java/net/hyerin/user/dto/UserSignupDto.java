package net.hyerin.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.hyerin.user.domain.Role;
import net.hyerin.user.domain.User;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupDto {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message="이메일 주소가 올바르지 않습니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min=6, max=12, message="6 자리 이상 12 자리 이하이어야 합니다.")
    private String password1;

    private String password2;

    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    @Size(min=2, max=30)
    private String name;

    private String phone;

    private MultipartFile profile;

    // 팩토리 메소드에 대한 설명 -> https://hyerin6.github.io/2020-04-05/0405/
    public User toEntityWithPasswordEncoder(String password) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .userType(Role.ROLE_RESTRICTED)
                .enabled(false)
                .likeCnt(0)
                .build();
    }

}

