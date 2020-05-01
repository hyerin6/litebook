package net.hyerin.images.repository;

import net.hyerin.images.domain.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {

    public Images save(Images images);

}
