package net.hyerin.post.dto;

import lombok.*;
import net.hyerin.post.domain.Post;
import net.hyerin.user.domain.User;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class InsertPostDto {

    @NotBlank(message = "내용을 입력하세요.")
    private String mainText;

    private Date startedDate;

    public Post toEntity(User user) {
        return Post.builder()
                .mainText(mainText)
                .startedDate(startedDate)
                .user(user)
                .build();
    }

}
