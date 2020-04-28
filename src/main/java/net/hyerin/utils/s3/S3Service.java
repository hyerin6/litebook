package net.hyerin.utils.s3;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    public void setS3Client();
    public String upload(MultipartFile file) throws IOException;
    public String userProfileUpload(MultipartFile file) throws IOException;

}
