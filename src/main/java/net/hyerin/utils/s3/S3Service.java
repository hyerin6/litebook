package net.hyerin.utils.s3;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import net.hyerin.images.domain.Images;

public interface S3Service {

    public void setS3Client();

    public String upload(MultipartFile file) throws IOException;

    public String userProfileUpload(MultipartFile file, String fileName) throws IOException;

    public void deleteFile(Images images);

}
