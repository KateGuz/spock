package ua.spock.spock.service;

import ua.spock.spock.entity.Lot;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ImageService {
    byte[] getImage(int imageId);
    List<Integer> getIds(int lotId);
    Map<Integer, Integer> getIds(List<Lot> lots);
    void saveImage(int lotId, InputStream imageStream);
}
