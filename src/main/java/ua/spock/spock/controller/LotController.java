package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.service.LotService;

import java.util.List;

@Controller
public class LotController {
    @Autowired
    private LotService lotService;

    @RequestMapping("/")
    public String getLots(ModelMap model) {
        List<Lot> list = lotService.getAll();
        model.addAttribute("lots", list);
        return "lots";
    }


    @RequestMapping("/category/{categoryId}")
    public String getLotsByCategory(ModelMap model, @PathVariable int categoryId) {
        model.addAttribute("lots", lotService.getLotsByCategory(categoryId));
        return "lots";
    }

}
