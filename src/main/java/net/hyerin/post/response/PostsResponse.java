package net.hyerin.post.response;

import java.util.List;

import net.hyerin.post.domain.Post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostsResponse {

	private List<Post> posts;

	private Long lastIdOfPosts;

}
