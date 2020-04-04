package net.hyerin.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    ROLE_RESTRICTED, // TODO 제한된 사용자
    IS_AUTHENTICATED_FULLY // TODO 인증된 사용자

}
