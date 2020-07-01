package net.hyerin.likes.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.hyerin.likes.domain.Likes;
import net.hyerin.likes.service.LikesService;
import net.hyerin.post.domain.Post;
import net.hyerin.post.service.PostService;
import net.hyerin.user.domain.User;
import net.hyerin.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LikesController {

	private UserService userService;
	private LikesService likesService;
	private PostService postService;

	public LikesController(UserService userService, LikesService likesService, PostService postService) {
		this.userService = userService;
		this.likesService = likesService;
		this.postService = postService;
	}

	@GetMapping(value="/likes/{id}")
	public String checkLike(@PathVariable("id") Long postId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByEmail(auth.getName());

		Post post = postService.getPost(postId);
		Likes likes = likesService.isCheck(user, post);

		if(likes == null) {
			log.info("한번도 누른적없음");
			likesService.checkLike(user, post);
		} else if (likes.getCheckLike()) {
			log.info("좋아요 취소할거임");
			likesService.cancelLike(user, post);
		} else {
			log.info("좋아요 누를거임");
			likesService.reCheckLike(user, post);
		}

		return "redirect:/users/profile";
	}

}
