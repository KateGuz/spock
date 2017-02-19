package ua.spock.spock.dao;

import ua.spock.spock.entity.Image;

import java.io.InputStream;

public interface ImageDao {
    Image getImage(int imageId);

    void saveImage(int lotId, InputStream imageStream);
}
