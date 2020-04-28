package net.hyerin.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.hyerin.images.domain.Images;
import sun.tools.tree.LongExpression;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
// JPA Hibernate가 객체를 생성하기 위해 필요한 기본 생성자 설정
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

    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Images profile;

    @Enumerated(EnumType.STRING)
    private Role userType;

    private boolean enabled;

}
