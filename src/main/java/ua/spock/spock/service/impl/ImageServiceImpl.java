package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.ImageDao;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.service.ImageService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService{
    @Autowired
    private ImageDao imageDao;

    @Override
    public byte[] getImage(int imageId) {
        return imageDao.getImage(imageId);
    }

    @Override
    public List<Integer> getLotImagesId(int lotId) {
        return imageDao.getLotImagesId(lotId);
    }

    @Override
    public Map<Integer, Integer> getIds(List<Lot> lots) {
        List<Integer> lotIds = new ArrayList<>();
        for (Lot lot : lots) {
            lotIds.add(lot.getId());
        }
        return imageDao.getIds(lotIds);
    }

    @Override
    public void saveImage(int lotId, InputStream imageStream) {
        imageDao.saveImage(lotId, imageStream);
    }
}
