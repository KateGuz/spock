package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.spock.spock.entity.*;
import ua.spock.spock.controller.util.LotDetailsWrapper;
import ua.spock.spock.filter.LotFilter;
import ua.spock.spock.service.BidService;
import ua.spock.spock.service.CategoryCacheService;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.UserService;
import ua.spock.spock.utils.LotJsonParser;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LotController {
    @Autowired
    private BidService bidService;
    @Autowired
    private LotService lotService;
    @Autowired
    private LotDetailsWrapper lotDetailsWrapper;

    @Autowired
    private CategoryCacheService category;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String getLots(ModelMap model, @RequestParam(value = "sortType", required = false) String sort) {
        LotFilter lotFilter = new LotFilter();
        lotFilter.setSortType(SortType.getTypeById(sort));
        List<Lot> tempLots = lotService.getLots(lotFilter);
        model.addAttribute("lots", lotDetailsWrapper.prepareData(tempLots));
        model.addAttribute("timeLeft", lotDetailsWrapper.getTimeLeft());
        model.addAttribute("isStarted", lotDetailsWrapper.getIsStarted());
        model.addAttribute("bidCount", lotDetailsWrapper.getBidCount());
        model.addAttribute("currentPrice", lotDetailsWrapper.getCurrentPrice());
        model.addAttribute("categories", category.getAllCategories());
        return "lots";
    }

    @RequestMapping("/category/{categoryId}")
    public String getLotByCategory(ModelMap model, @RequestParam(value = "sortType", required = false) String sort, @PathVariable Integer categoryId) {
        LotFilter lotFilter = new LotFilter();
        lotFilter.setSortType(SortType.getTypeById(sort));
        lotFilter.setCategoryId(categoryId);
        List<Lot> tempLots = lotService.getLots(lotFilter);
        model.addAttribute("lots", lotDetailsWrapper.prepareData(tempLots));
        model.addAttribute("timeLeft", lotDetailsWrapper.getTimeLeft());
        model.addAttribute("isStarted", lotDetailsWrapper.getIsStarted());
        model.addAttribute("bidCount", lotDetailsWrapper.getBidCount());
        model.addAttribute("currentPrice", lotDetailsWrapper.getCurrentPrice());
        model.addAttribute("categories", category.getAllCategories());
        return "lots";
    }

    @RequestMapping("/lot/{lotId}")
    public String getLotById(ModelMap model, @PathVariable int lotId) {
        Lot lot = lotService.getById(lotId);
        LotDetailsWrapper lotDetailsWrapper = new LotDetailsWrapper();
        String timeLeft = lotDetailsWrapper.getTimeLeft(lot);
        String endDate = lotDetailsWrapper.getEndDate(lot);
        double currentPrice = lotDetailsWrapper.getCurrentPrice(lot);
        int bidCount = bidService.getBidCountForLot(lotId);
        User user = userService.get(lot.getUser().getId());
        model.addAttribute("user", user);
        model.addAttribute("lot", lot);
        model.addAttribute("timeLeft", timeLeft);
        model.addAttribute("endDate", endDate);
        model.addAttribute("currentPrice", currentPrice);
        model.addAttribute("bidCount", bidCount);
        return "lot";
    }

    @RequestMapping("/lot")
    public String add(ModelMap model, HttpSession session) {
        if (session.getAttribute("loggedUser") != null) {
            model.addAttribute("categories", category.getAllCategories());
            return "addLot";
        } else {
            return "lots";
        }
    }

    @RequestMapping(value = "/lot", method = RequestMethod.POST)
    public ResponseEntity addNewLot(@RequestBody String json, HttpSession session) {
        Lot lot = LotJsonParser.jsonToLot(json);
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == lot.getUser().getId()) || (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN))) {
                lotService.add(lot);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = " /lot/{lotId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLot(@PathVariable("lotId") int id, HttpSession session) {
        Lot lot = lotService.getById(id);
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == lot.getUser().getId()) || (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN))) {
                lotService.delete(id);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping("/lot/{lotId}/edit")
    public String editLot(ModelMap model, @PathVariable int lotId, HttpSession session) {
        Lot lot = lotService.getById(lotId);
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == lot.getUser().getId()) || (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN))) {
                List<Category> allCategories = category.getAllCategories();
                model.addAttribute("lot", lot);
                model.addAttribute("categories", allCategories);
                return "editLot";
            } else {
                return "lots";
            }
        } else {
            return "lots";
        }
    }

    @RequestMapping(value = "/lot/{id}", method = RequestMethod.PUT)
    public String saveEditedLot(@PathVariable Integer id, @RequestBody String json, HttpSession session) {
        Lot lot = LotJsonParser.jsonToLot(json);
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == lot.getUser().getId()) || (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN))) {
                lot.setId(id);
                lotService.edit(lot);
                int userId = lot.getUser().getId();
                return "redirect:/user/" + userId;
            } else {
                return "lots";
            }
        } else {
            return "lots";
        }
    }
}

