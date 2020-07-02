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
			likesService.checkLike(user, post);
			userService.checkLike(post.getUser());
		} else if (likes.getCheckLike()) {
			likesService.cancelLike(user, post);
			userService.cancelLike(post.getUser());
		} else {
			likesService.reCheckLike(user, post);
			userService.checkLike(post.getUser());
		}

		return "redirect:/users/" + post.getUser().getId();
	}

}
