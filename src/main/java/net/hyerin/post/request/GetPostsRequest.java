package net.hyerin.post.request;

import lombok.Getter;

@Getter
public class GetPostsRequest {

	private Long userId;

	private Long lastIdOfPosts;

}
