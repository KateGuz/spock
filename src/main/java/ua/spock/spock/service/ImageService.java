package ua.spock.spock.service;

import java.io.InputStream;

public interface ImageService {
    byte[] getImage(int imageId);

    void saveImage(int lotId, InputStream imageStream);
}
