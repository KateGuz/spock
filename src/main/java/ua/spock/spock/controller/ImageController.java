package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
        byte[] image = imageService.getImage(imageId).getBytes();
        response.getOutputStream().write(image);
    }

    @RequestMapping(value = "/userImage/{userId}")
    public void getUserImage(HttpServletResponse response, @PathVariable int userId) throws IOException {
        response.setContentType("image/jpg");
        byte[] image = imageService.getUserImage(userId).getBytes();
        response.getOutputStream().write(image);
    }
}
