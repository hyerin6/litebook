package net.hyerin.images.service;

import net.hyerin.images.domain.Images;

public interface ImagesService {

    public Images saveImage(String filePath, String fileName);

    public void deleteImage(Long id);

}
