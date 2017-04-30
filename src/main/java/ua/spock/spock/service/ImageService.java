package ua.spock.spock.service;

import ua.spock.spock.entity.Image;

import java.io.InputStream;

public interface ImageService {
    Image getImage(int imageId);

    Image getUserImage(int userId);

    void saveUserImage(Image image);

    void saveLotImage(Image image);

    void editPrimaryLotImage(Image image);

}
