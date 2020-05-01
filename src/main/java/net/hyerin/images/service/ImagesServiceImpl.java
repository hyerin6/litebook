package net.hyerin.images.service;

import net.hyerin.images.domain.Images;
import net.hyerin.images.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("imagesService")
public class ImagesServiceImpl implements ImagesService{

    private ImagesRepository imagesRepository;

    @Autowired
    public ImagesServiceImpl(ImagesRepository imagesRepository){
        this.imagesRepository = imagesRepository;
    }

    @Override
    public Images saveUserProfile(String filePath){
        Images profile = new Images(filePath);
        return imagesRepository.save(profile);
    }

}
