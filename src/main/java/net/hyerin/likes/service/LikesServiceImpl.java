package net.hyerin.likes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.hyerin.likes.domain.Likes;
import net.hyerin.likes.repository.LikesRepository;
import net.hyerin.post.domain.Post;
import net.hyerin.user.domain.User;

@Service("likesService")
public class LikesServiceImpl implements LikesService {

	private LikesRepository likesRepository;

	public LikesServiceImpl(LikesRepository likesRepository) {
		this.likesRepository = likesRepository;
	}

	@Override
	public void checkLike(User user, Post post) {
		Likes likes = Likes.builder()
			.user(user)
			.post(post)
			.checkLike(true)
			.build();
		likesRepository.save(likes);
	}

	@Override
	@Transactional
	public void cancelLike(User user, Post post) {
		Likes likes = likesRepository.findByUserIdAndPostId(user.getId(), post.getId());
		likes.setCheckLike(false);
	}

	@Override
	public Likes isCheck(User user, Post post) {
		return likesRepository.findByUserIdAndPostId(user.getId(), post.getId());
	}

	@Override
	@Transactional
	public void reCheckLike(User user, Post post) {
		Likes likes = likesRepository.findByUserIdAndPostId(user.getId(), post.getId());
		likes.setCheckLike(true);
	}

	@Override
	public int countLikes(Long postId) {
		return likesRepository.findByLikes(postId);
	}

}
