package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ua.spock.spock.entity.Image;
import ua.spock.spock.service.ImageService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

@Controller
public class ImageController {
    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image/{imageId}")
    public void getImage(HttpServletResponse response, @PathVariable int imageId) throws IOException {
        response.setContentType("image/jpg");
        byte[] image = imageService.getImage(imageId).getBytes();
        response.getOutputStream().write(image);
    }

    @RequestMapping(value = "/userImage/{userId}")
    public void getUserImage(HttpServletResponse response, @PathVariable int userId) throws IOException {
        response.setContentType("image/jpg");
        byte[] image = imageService.getUserImage(userId).getBytes();
        response.getOutputStream().write(image);
    }

    @RequestMapping(value = "/uploadUserImage/{userId}", method = RequestMethod.POST)
    public void uploadUserImage(MultipartHttpServletRequest request, @PathVariable int userId) throws IOException {
        Image image = new Image();
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = request.getFile(itr.next());
        try {
            image.setBytes(mpf.getBytes());
            image.setId(userId);
            imageService.saveUserImage(image);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @RequestMapping(value = "/uploadPrimaryLotImage/{lotId}", method = RequestMethod.POST)
    public void uploadPrimaryLotImage(MultipartHttpServletRequest request, @PathVariable int lotId) throws IOException {
        Image image = new Image();
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = request.getFile(itr.next());
        try {
            image.setBytes(mpf.getBytes());
            image.setId(lotId);
            imageService.savePrimaryLotImage(image);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @RequestMapping(value = "/uploadSecondaryLotImage/{lotId}", method = RequestMethod.POST)
    public void uploadSecondaryLotImage(MultipartHttpServletRequest request, @PathVariable int lotId) throws IOException {
        Image image = new Image();
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = request.getFile(itr.next());
        try {
            image.setBytes(mpf.getBytes());
            image.setId(lotId);
            imageService.saveSecondaryLotImage(image);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
