package ua.spock.spock.dao;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ImageDao {
    byte[] getImage(int imageId);
    List<Integer> getLotImagesId(int lotId);
    Map<Integer, Integer> getIds(List<Integer> lotIds);
    void saveImage(int lotId, InputStream imageStream);
}
