package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.spock.spock.dto.LotDtoConstructor;
import ua.spock.spock.entity.*;
import ua.spock.spock.filter.LotFilter;
import ua.spock.spock.service.BidService;
import ua.spock.spock.service.ImageService;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.cache.CategoryCache;
import ua.spock.spock.utils.LotRequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class LotController {
    @Autowired
    private BidService bidService;
    @Autowired
    private LotService lotService;
    @Autowired
    private CategoryCache categoryCache;
    @Autowired
    private LotDtoConstructor lotDtoConstructor;
    @Autowired
    private ImageService imageService;

    @RequestMapping("/")
    public String getLots(ModelMap model, @RequestParam(value = "sortType", required = false) String sort,
                          @RequestParam(value = "currency", required = false) String currency,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int page, HttpSession session) {
        if (currency != null) {
            session.setAttribute("currency", currency);
        }
        if (session.getAttribute("currency") == null) {
            session.setAttribute("currency", "UAH");
        }
        currency = (String) session.getAttribute("currency");
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

    public String getLotByCategory(ModelMap model, @RequestParam(value = "sortType", required = false) String sort,
                                   @RequestParam(value = "currency", required = false) String currency,
                                   @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                   @PathVariable Integer categoryId, HttpSession session) {
        if (currency != null) {
            session.setAttribute("currency", currency);
        }
        if (session.getAttribute("currency") == null) {
            session.setAttribute("currency", "UAH");
        }
        currency = (String) session.getAttribute("currency");
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
        if (currency != null) {
            session.setAttribute("currency", currency);
        }
        if (session.getAttribute("currency") == null) {
            session.setAttribute("currency", "UAH");
        }
        currency = (String) session.getAttribute("currency");
        Lot lot = lotService.getById(lotId);
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
    public ResponseEntity addNewLot(@RequestParam(name = "primaryImage", required = false) MultipartFile primaryImage,
                                    @RequestParam(name = "secondaryImage", required = false) MultipartFile secondaryImage,
                                    HttpServletRequest httpServletRequest, HttpSession session) throws IOException {
        Lot lot = LotRequestParser.requestToLot(httpServletRequest);
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == lot.getUser().getId()) || (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN))) {
                if (primaryImage != null) {
                    Image image = new Image();
                    try {
                        image.setBytes(primaryImage.getBytes());
                    } catch (IOException e) {
                        throw new IOException(e);
                    }
                    image.setType("M");
                    lot.setPrimaryImage(image);
                }
                if (secondaryImage != null) {
                    Image image = new Image();
                    try {
                        image.setBytes(secondaryImage.getBytes());
                    } catch (IOException e) {
                        throw new IOException(e);
                    }
                    image.setType("C");
                    lot.setSecondaryImage(image);
                }
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
                return "editLot";
            }
        }
        return "error";
    }

    @RequestMapping(value = "/lot/{id}", method = RequestMethod.POST)
    public ResponseEntity saveEditedLot(@PathVariable Integer id, @RequestParam(name = "primaryImage", required = false)
            MultipartFile primaryImage, @RequestParam(name = "secondaryImage", required = false) MultipartFile secondaryImage,
                                        HttpServletRequest httpServletRequest, HttpSession session) throws IOException {
        Lot lot = LotRequestParser.requestToLot(httpServletRequest);
        lot.setId(id);
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == lot.getUser().getId()) || (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN))) {
                if (primaryImage != null) {
                    Image image = new Image();
                    try {
                        image.setBytes(primaryImage.getBytes());
                    } catch (IOException e) {
                        throw new IOException(e);
                    }
                    image.setType("M");
                    image.setId(id);
                    imageService.editPrimaryLotImage(image);
                }
                if (secondaryImage != null) {
                    Image image = new Image();
                    try {
                        image.setBytes(secondaryImage.getBytes());
                    } catch (IOException e) {
                        throw new IOException(e);
                    }
                    image.setType("C");
                    image.setId(id);
                    imageService.saveLotImage(image);
                }
                lotService.edit(lot);

                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
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

