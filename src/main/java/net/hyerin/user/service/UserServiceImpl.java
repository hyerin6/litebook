package net.hyerin.user.service;

import static net.hyerin.user.domain.Role.*;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import net.hyerin.email.service.EmailService;
import net.hyerin.images.domain.Images;
import net.hyerin.images.service.ImagesService;
import net.hyerin.user.domain.User;
import net.hyerin.user.dto.UserModifyDto;
import net.hyerin.user.dto.UserSignupDto;
import net.hyerin.user.repository.UserRepository;
import net.hyerin.utils.s3.S3ServiceImpl;
import net.hyerin.utils.security.EncryptionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private EmailService emailService;
    private S3ServiceImpl s3Service;
    private ImagesService imagesService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           EmailService emailService,
                           S3ServiceImpl s3Service,
                           ImagesService imagesService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.imagesService = imagesService;
        this.s3Service = s3Service;
    }

    @Override
    @Transactional
    public void signup(UserSignupDto userSignupDto) throws Exception {
        User user = userSignupDto.toEntityWithPasswordEncoder(EncryptionUtils.encryptSHA256(userSignupDto.getPassword1()));
        emailService.sendMail(user.getEmail());

        if(!userSignupDto.getProfile().isEmpty()) {
            String randomUUID = UUID.randomUUID().toString();
            String path = s3Service.userProfileUpload(userSignupDto.getProfile(), randomUUID);
            Images profile = imagesService.saveImage(path, randomUUID);
            user.setProfile(profile);
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserType(String email){
        User user = userRepository.findOneByEmail(email);
        user.setUserType(IS_AUTHENTICATED_FULLY);
        user.setEnabled(true);
    }

    @Override
    @Transactional
    public boolean hasErrors(UserSignupDto userSignupDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return true;

        if (!userSignupDto.getPassword1().equals(userSignupDto.getPassword2())) {
            bindingResult.rejectValue("password2", null, "비밀번호가 일치하지 않습니다.");
            return true;
        }

        User user = userRepository.findOneByEmail(userSignupDto.getEmail());
        if (user != null) {
            if(!user.isEnabled()){
                userRepository.deleteByEmail(user.getEmail());
                return true;
            }
            bindingResult.rejectValue("email", null, "사용자 아이디가 중복됩니다.");
            return true;
        }
        return false;
    }

    @Override
    public User findById(Long id){
        return userRepository.findById(id).get();
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findOneByEmail(email);
    }

    @Override
    @Transactional
    public User modifyProfile(UserModifyDto userModifyDto, Long userId) throws Exception {
        User user = userRepository.findById(userId).get();

        if(!userModifyDto.getName().isEmpty()) {
            user.setName(userModifyDto.getName());
        }

        if(!userModifyDto.getPassword().isEmpty()) {
            user.setPassword(EncryptionUtils.encryptSHA256(userModifyDto.getPassword()));
        }

        if(!userModifyDto.getPhone().isEmpty()) {
            user.setPhone(userModifyDto.getPhone());
        }

        if(!userModifyDto.getProfile().isEmpty()) {
            s3Service.deleteFile(user.getProfile());
            String randomUUID = UUID.randomUUID().toString();
            String path = s3Service.userProfileUpload(userModifyDto.getProfile(), randomUUID);
            Images profile = imagesService.saveImage(path, randomUUID);
            user.setProfile(profile);
        }

        return user;
    }

    // @Override
    // public List<User> getUsers(String name) {
    //     final List<User> users = get(postId, userId);
    //     return users;
    // }
    //
    // private List<Post> get(String name, Long id) {
    //     return id == null ?
    //         this.postRepository.findTop5ByUser_IdOrderByStartedDateDesc(userId) :
    //         this.postRepository.findTop5ByUser_IdAndIdLessThanOrderByIdDescStartedDateDesc(userId, id);
    // }
    //
    // @Override
    // public Long getMinIdOfPosts(Long userId){
    //     return postRepository.findMinIdByUserId(userId);
    // }

}
