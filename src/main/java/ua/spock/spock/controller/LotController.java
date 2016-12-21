package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.spock.spock.controller.util.LotDetails;
import ua.spock.spock.controller.util.ModelMapAttributesWrapper;
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
    @Autowired
    private ModelMapAttributesWrapper modelMapAttributesWrapper;

    @RequestMapping("/")
    public String getLots(ModelMap model, @RequestParam(value = "sortType", required = false) String sort, @RequestParam(value = "page", required = false) Integer page) {
        if (page == null) {
            page = 1;
        }
        int lotsPerPage = 9;
        LotFilter lotFilter = new LotFilter();
        lotFilter.setSortType(SortType.getTypeById(sort));
        List<Lot> lots = lotService.getLots(lotFilter, page, lotsPerPage);
        int pageCount = (int) Math.ceil(lotService.getLotCount(lotFilter) * 1.0 / lotsPerPage);
        LotDetails lotDetails = lotDetailsWrapper.prepareData(lots);
        modelMapAttributesWrapper.fillLotAtributes(model,lotDetails);
        model.addAttribute("categories", category.getAllCategories());
        model.addAttribute("page", page);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("sortType", sort);
        return "lots";
    }

    @RequestMapping("/category/{categoryId}")
    public String getLotByCategory(ModelMap model, @RequestParam(value = "sortType", required = false) String sort,  @RequestParam(value = "page", required = false) Integer page, @PathVariable Integer categoryId) {
        if (page == null) {
            page = 1;
        }
        int lotsPerPage = 9;
        LotFilter lotFilter = new LotFilter();
        lotFilter.setSortType(SortType.getTypeById(sort));
        lotFilter.setCategoryId(categoryId);
        List<Lot> lots = lotService.getLots(lotFilter, page, lotsPerPage);
        int pageCount = (int) Math.ceil(lotService.getLotCount(lotFilter) * 1.0 / lotsPerPage);
        LotDetails lotDetails = lotDetailsWrapper.prepareData(lots);
        modelMapAttributesWrapper.fillLotAtributes(model,lotDetails);
        model.addAttribute("categories", category.getAllCategories());
        model.addAttribute("page", page);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("sortType", sort);
        return "lots";
    }

    @RequestMapping("/lot/{lotId}")
    public String getLotById(ModelMap model, @PathVariable int lotId) {
        Lot lot = lotService.getById(lotId);
        LotDetailsWrapper lotDetailsWrapper = new LotDetailsWrapper();
        String timeLeft = lotDetailsWrapper.getTimeLeft(lot);
        String endDate = lotDetailsWrapper.getEndDate(lot);
        boolean isStarted = lotDetailsWrapper.isStarted(lot);
        boolean isNotFinished = lotDetailsWrapper.isNotFinished(lot);
        double currentPrice = lotDetailsWrapper.getCurrentPrice(lot);
        int bidCount = bidService.getBidCountForLot(lotId);
        User user = userService.get(lot.getUser().getId());
        model.addAttribute("user", user);
        model.addAttribute("lot", lot);
        model.addAttribute("timeLeft", timeLeft);
        model.addAttribute("isStarted",isStarted);
        model.addAttribute("isNotFinished", isNotFinished);
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

    @RequestMapping(value = "/lot/{lotId}/quickBuy", method = RequestMethod.POST)
    public ResponseEntity quickBuy(@PathVariable Integer lotId, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        Lot lot = lotService.getById(lotId);
        Bid bid = new Bid();
        bid.setValue(lot.getQuickBuyPrice());
        bid.setLot(lot);
        bid.setUser(user);
        bidService.add(bid);
        lotService.closeLot(lot);
        return new ResponseEntity(HttpStatus.OK);
    }
}

