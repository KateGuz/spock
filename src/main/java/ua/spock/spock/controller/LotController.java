package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.spock.spock.entity.*;
import ua.spock.spock.filter.LotFilter;
import ua.spock.spock.service.BidService;
import ua.spock.spock.service.LotService;
import ua.spock.spock.dto.LotDtoConstructor;
import ua.spock.spock.service.UserService;
import ua.spock.spock.service.cache.CategoryCache;
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
    private UserService userService;
    @Autowired
    private CategoryCache categoryCache;
    @Autowired
    private LotDtoConstructor lotDtoConstructor;

    @RequestMapping("/")
    public String getLots(ModelMap model, @RequestParam(value = "sortType", required = false) String sort, @RequestParam(value = "currency", required = false) String currency, @RequestParam(value = "page", required = false, defaultValue = "1") int page, HttpSession session) {
        currency = handleCurrency(currency, session);
        LotFilter lotFilter = new LotFilter();
        lotFilter.setSortType(SortType.getTypeById(sort));
        lotFilter.setPage(page);
        List<Lot> lots = lotService.getLots(lotFilter);
        int pageCount = lotService.getPageCount(lotFilter);
        model.addAttribute("lots", lotDtoConstructor.constructListOfLots(lots, Currency.valueOf(currency)));
        model.addAttribute("categories", categoryCache.getAllCategories());
        model.addAttribute("currency", currency);
        model.addAttribute("page", page);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("sortType", sort);
        return "lots";
    }

    @RequestMapping("/category/{categoryId}")

    public String getLotByCategory(ModelMap model, @RequestParam(value = "sortType", required = false) String
            sort, @RequestParam(value = "currency", required = false) String currency, @RequestParam(value = "page", required = false, defaultValue = "1") int page, @PathVariable Integer
                                           categoryId, HttpSession session) {
        currency = handleCurrency(currency, session);
        LotFilter lotFilter = new LotFilter();
        lotFilter.setSortType(SortType.getTypeById(sort));
        lotFilter.setCategoryId(categoryId);
        lotFilter.setPage(page);
        List<Lot> lots = lotService.getLots(lotFilter);
        int pageCount = lotService.getPageCount(lotFilter);
        model.addAttribute("lots", lotDtoConstructor.constructListOfLots(lots, Currency.valueOf(currency)));
        model.addAttribute("categories", categoryCache.getAllCategories());
        model.addAttribute("currency", currency);
        model.addAttribute("page", page);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("sortType", sort);
        return "lots";
    }

    @RequestMapping("/lot/{lotId}")
    public String getLotById(ModelMap model, @PathVariable int lotId,
                             @RequestParam(value = "currency", required = false) String currency, HttpSession session) {
        currency = handleCurrency(currency, session);
        Lot lot = lotService.getById(lotId);
        User user = (User) session.getAttribute("loggedUser");
        if (user != null) {
            model.addAttribute("subscribed", userService.checkIfSubscribed(user, lot));
        }
        model.addAttribute("lotDto", lotDtoConstructor.constructOneLot(lot, Currency.valueOf(currency)));
        model.addAttribute("currency", currency);
        return "lot";
    }

    @RequestMapping("/lot")
    public String add(ModelMap model, @RequestParam(value = "currency", required = false) String currency, HttpSession session) {
        if (session.getAttribute("loggedUser") != null) {
            if (currency != null) {
                session.setAttribute("currency", currency);
            }
            model.addAttribute("categories", categoryCache.getAllCategories());
            model.addAttribute("currency", session.getAttribute("currency"));
            return "addLot";
        }
        return "error";
    }

    @RequestMapping(value = "/lot", method = RequestMethod.POST)
    public ResponseEntity addNewLot(@RequestBody String json, HttpSession session) {
        Lot lot = LotJsonParser.jsonToLot(json);
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == lot.getUser().getId()) || (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN))) {
                lotService.add(lot);
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/lot/{lotId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLot(@PathVariable("lotId") int id, HttpSession session) {
        Lot lot = lotService.getById(id);
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == lot.getUser().getId()) || (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN))) {
                lotService.delete(id);
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping("/lot/{lotId}/edit")
    public String editLot(ModelMap model, @PathVariable int lotId,
                          @RequestParam(value = "currency", required = false) String currency, HttpSession session) {
        Lot lot = lotService.getById(lotId);
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == lot.getUser().getId()) || (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN))) {
                if (currency != null) {
                    session.setAttribute("currency", currency);
                }
                List<Category> allCategories = categoryCache.getAllCategories();
                model.addAttribute("lot", lot);
                model.addAttribute("categories", allCategories);
                model.addAttribute("currency", session.getAttribute("currency"));
                Boolean scheduled = lotService.checkIfScheduled(lot);
                model.addAttribute("scheduled", scheduled);
                return "editLot";
            }
        }
        return "error";
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
            }
        }
        return "error";
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

    @RequestMapping(value = "/lot/{lotId}/subscribe", method = RequestMethod.POST)
    public ResponseEntity subscribe(@PathVariable Integer lotId, HttpSession session) {
        if (session.getAttribute("loggedUser") != null) {
            User user = (User) session.getAttribute("loggedUser");
            Lot lot = lotService.getById(lotId);
            if (!userService.checkIfSubscribed(user, lot)) {
                userService.subscribe(user, lot);
            }
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/lot/{lotId}/unsubscribe", method = RequestMethod.POST)
    public ResponseEntity unSubscribe(@PathVariable Integer lotId, HttpSession session) {
        if (session.getAttribute("loggedUser") != null) {
            User user = (User) session.getAttribute("loggedUser");
            Lot lot = lotService.getById(lotId);
            userService.unSubscribe(user, lot);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    private String handleCurrency(String currency, HttpSession session) {
        if (currency != null) {
            session.setAttribute("currency", currency);
        }
        if (session.getAttribute("currency") == null) {
            session.setAttribute("currency", "UAH");
        }
        return (String) session.getAttribute("currency");
    }
}

