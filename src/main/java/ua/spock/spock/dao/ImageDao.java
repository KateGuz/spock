package ua.spock.spock.dao;

import java.io.InputStream;

public interface ImageDao {
    byte[] getImage(int imageId);

    void saveImage(int lotId, InputStream imageStream);
}
