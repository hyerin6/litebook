package net.hyerin.post.service;

import net.hyerin.post.domain.Post;
import net.hyerin.post.dto.InsertPostDto;
import net.hyerin.post.repository.PostRepository;
import net.hyerin.user.domain.User;
import net.hyerin.user.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private PostRepository postRepository;
    private CustomUserDetailsService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CustomUserDetailsService userService){
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public void insertPost(InsertPostDto insertPostDto) throws ParseException {
        // starteDate 저장
        String now =  formatter.format(new Date());
        Date startedDate =  formatter.parse(now);
        insertPostDto.setStartedDate(startedDate);

        // user 정보 저장
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUser(email);

        // Insert post
        Post post = insertPostDto.toEntity(user);
        postRepository.save(post);
    }

    @Override
    public List<Post> findByUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUser(email);
        return postRepository.findByUserId(user.getId());
    }

    @Override
    public List<Post> findByFriendId(Long id){
        return postRepository.findByUserId(id);
    }

}
