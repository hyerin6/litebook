package net.hyerin.user.service;

import lombok.extern.slf4j.Slf4j;
import net.hyerin.email.service.EmailService;
import net.hyerin.images.domain.Images;
import net.hyerin.images.service.ImagesService;
import net.hyerin.user.domain.User;
import net.hyerin.user.dto.UserSignupDto;
import net.hyerin.user.repository.UserRepository;
import net.hyerin.utils.s3.S3ServiceImpl;
import net.hyerin.utils.security.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import static net.hyerin.user.domain.Role.IS_AUTHENTICATED_FULLY;

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
    }

    @Override
    @Transactional
    public void signup(UserSignupDto userSignupDto) throws Exception {
        User user = userSignupDto.toEntityWithPasswordEncoder(EncryptionUtils.encryptSHA256(userSignupDto.getPassword1()));
        emailService.sendMail(user.getEmail());

        if(!userSignupDto.getProfile().isEmpty()) {
            String path = s3Service.userProfileUpload(userSignupDto.getProfile()); // aws s3 이미지 저장
            Images profile = imagesService.saveUserProfile(path); // Images 테이블에 저장
            user.setProfile(profile); // user에 profile 저장
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

}
