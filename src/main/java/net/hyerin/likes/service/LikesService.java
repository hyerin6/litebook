package net.hyerin.likes.service;

import net.hyerin.likes.domain.Likes;

public interface LikesService {

	public int checkLike(Long userId, Long postId);

	public int cancelLike(Long userId, Long postId);

	public Likes isCheck(Long userId, Long postId);

}
