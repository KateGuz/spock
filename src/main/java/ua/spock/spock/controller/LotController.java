package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.spock.spock.service.LotService;

@Controller
public class LotController {
    @Autowired
    private LotService lotService;

    @RequestMapping("/lot/{lotId}")
    public String getLotById(ModelMap model, @PathVariable int lotId) {
        model.addAttribute("lot", lotService.getLotById(lotId));
        return "lot";
    }
}
