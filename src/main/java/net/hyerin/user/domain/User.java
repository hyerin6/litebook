package net.hyerin.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor

// TODO JPA Hibernate가 객체를 생성하기 위해 필요한 기본 생성자 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String phone;

    private String profile;

    // TODO enum의 이름을 저장한다.
    @Enumerated(EnumType.STRING)
    private Role userType;

    private boolean enabled;

}
