package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.ImageDao;
import ua.spock.spock.service.ImageService;

import java.io.InputStream;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageDao imageDao;

    @Override
    public byte[] getImage(int imageId) {
        return imageDao.getImage(imageId);
    }

    @Override
    public void saveImage(int lotId, InputStream imageStream) {
        imageDao.saveImage(lotId, imageStream);
    }
}
