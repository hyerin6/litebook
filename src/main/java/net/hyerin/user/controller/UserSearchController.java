package net.hyerin.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import net.hyerin.user.domain.User;
import net.hyerin.user.service.UserService;

import lombok.Builder;
import lombok.Getter;

@RestController
public class UserSearchController {

	private UserService userService;

	public UserSearchController(UserService userService){
		this.userService = userService;
	}

	// @RequestMapping(value = "users/search", method = RequestMethod.POST)
	// @ResponseBody
	// public UserSearchResponse getMyPosts(@RequestBody GetUsersRequest getUsersRequest) {
	// 	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	// 	User user = userService.findByEmail(auth.getName());
	//
	// 	// List<Post> posts = postService.getPosts(getPostsRequest.getLastIdOfPosts(), user.getId());
	// 	// Long lastIdOfPosts = posts.isEmpty() ?
	// 	// 	null : posts.get(posts.size() - 1).getId();
	//
	// 	List<User> users = userService.();
	//
	// 	UserSearchResponse result = UserSearchResponse.builder()
	// 		.users(users)
	// 		.lastIdOfPosts(lastIdOfPosts)
	// 		.build();
	// 	return result;
	// }

	@Getter
	static class GetUsersRequest{
		private Long lastIdOfUsers;
		private String name;
	}

	@Getter
	@Builder
	static class UserSearchResponse{
		private List<User> users;
		private Long lastIdOfUsers;
	}

}
