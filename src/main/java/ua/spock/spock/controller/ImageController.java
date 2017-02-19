package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ua.spock.spock.service.ImageService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ImageController {
    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image/{imageId}")
    public void getImage(HttpServletResponse response, @PathVariable int imageId) throws IOException {
        response.setContentType("image/jpg");
        byte[] image = imageService.getImage(imageId);
        response.getOutputStream().write(image);
    }

    @RequestMapping(value = "/lot/{lotId}/image", method = RequestMethod.POST)
    public ResponseEntity uploadImage(@RequestParam CommonsMultipartFile image, @PathVariable int lotId) throws IOException {
        imageService.saveImage(lotId, image.getInputStream());
        return new ResponseEntity(HttpStatus.OK);
    }
}
