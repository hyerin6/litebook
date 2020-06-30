package net.hyerin.likes.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import net.hyerin.likes.domain.Likes;
import net.hyerin.likes.service.LikesService;
import net.hyerin.user.domain.User;
import net.hyerin.user.service.UserService;

@Controller
public class LikesController {

	private UserService userService;
	private LikesService likesService;

	public LikesController(UserService userService, LikesService likesService) {
		this.userService = userService;
		this.likesService = likesService;
	}

	@GetMapping(value="/likes/{id}")
	public String checkLike(@PathVariable("id") Long postId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByEmail(auth.getName());

		Likes likes = likesService.isCheck(user.getId(), postId);

		if(likes == null) {
			likesService.checkLike(user.getId(), postId);
		} else if (likes.getCheck()) {
			likesService.cancelLike(user.getId(), postId);
		} else {
			likesService.checkLike(user.getId(), postId);
		}

		return "";
	}

}
