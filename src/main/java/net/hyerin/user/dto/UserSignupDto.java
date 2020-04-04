package net.hyerin.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.hyerin.user.domain.Role;
import net.hyerin.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSignupDto {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message="이메일 주소가 올바르지 않습니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min=6, max=12, message="6 자리 이상 12 자리 이하이어야 합니다.")
    private String password1;

    @NotBlank(message="비밀번호를 한번 더 입력하세요")
    private String password2;

    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    @Size(min=2, max=30)
    private String name;

    @Pattern(regexp = "[0-9]{10,11}", message = "10-11자리의 숫자만 입력가능합니다.")
    private String phone;

    private String profile;

    public User toEntityWithPasswordEncoder(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password1))
                .name(name)
                .phone(phone)
                .profile(profile)
                .userType(Role.ROLE_RESTRICTED)
                .enabled(false)
                .build();
    }

}

