package net.hyerin.likes.service;

import org.springframework.stereotype.Service;

import net.hyerin.likes.domain.Likes;
import net.hyerin.likes.repository.LikesRepository;

@Service
public class LikesServiceImpl implements LikesService {

	private LikesRepository likesRepository;

	public LikesServiceImpl() {
		this.likesRepository = likesRepository;
	}

	public int checkLike(Long userId, Long postId) {

		return 1;
	}

	public int cancelLike(Long userId, Long postId) {
		return 1;
	}

	public Likes isCheck(Long userId, Long postId) {
		return likesRepository.findByUser_IdAndPost_Id(userId, postId);
	}


}
