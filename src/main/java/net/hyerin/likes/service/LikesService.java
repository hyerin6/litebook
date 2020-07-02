package net.hyerin.likes.service;

import net.hyerin.likes.domain.Likes;
import net.hyerin.post.domain.Post;
import net.hyerin.user.domain.User;

public interface LikesService {

	public void checkLike(User user, Post post);

	public void cancelLike(User user, Post post);

	public void reCheckLike(User user, Post post);

	public Likes isCheck(User user, Post post);

	public void deletePost(Long postId);

}
