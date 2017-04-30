package ua.spock.spock.dao;

import ua.spock.spock.entity.Image;

import java.io.InputStream;

public interface ImageDao {
    Image getImage(int imageId);

    Image getUserImage(int userId);

    void saveUserImage(Image image);

    void saveLotImage(Image image);

    void editPrimaryLotImage(Image image);
}