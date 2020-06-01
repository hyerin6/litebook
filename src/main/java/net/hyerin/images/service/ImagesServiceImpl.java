package net.hyerin.images.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.hyerin.images.domain.Images;
import net.hyerin.images.repository.ImagesRepository;
import net.hyerin.utils.s3.S3Service;

@Service
@Transactional
public class ImagesServiceImpl implements ImagesService {

    private ImagesRepository imagesRepository;

    private S3Service s3Service;

    public ImagesServiceImpl(ImagesRepository imagesRepository, S3Service s3Service){
        this.imagesRepository = imagesRepository;
        this.s3Service = s3Service;
    }

    @Override
    public Images saveImage(String filePath, String fileName) {
        Images images = new Images(filePath, fileName);
        return imagesRepository.save(images);
    }

    @Override
    public void deleteImage(Long id) {
        s3Service.deleteFile(imagesRepository.findOneById(id));
        imagesRepository.deleteById(id);
    }

}
