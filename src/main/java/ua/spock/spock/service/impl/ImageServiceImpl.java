package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.ImageDao;
import ua.spock.spock.entity.Image;
import ua.spock.spock.service.ImageService;

import java.io.InputStream;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageDao imageDao;

    @Override
    public Image getImage(int imageId) {
        return imageDao.getImage(imageId);
    }

    @Override
    public Image getUserImage(int userId) {
        return imageDao.getUserImage(userId);
    }

    @Override
    public void saveUserImage(Image image) {
        imageDao.saveUserImage(image);
    }

    @Override
    public void saveLotImage(Image image) {
        imageDao.saveLotImage(image);
    }

    @Override
    public void editPrimaryLotImage(Image image) {
        imageDao.editPrimaryLotImage(image);
    }
}
