package ua.spock.spock.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.spock.spock.entity.Bid;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.User;
import ua.spock.spock.entity.UserType;
import ua.spock.spock.service.BidService;
import ua.spock.spock.service.LotService;
import ua.spock.spock.utils.BidJsonParser;

import javax.servlet.http.HttpSession;

@Controller
public class BidController {
    @Autowired
    private BidService bidService;
    @Autowired
    private LotService lotService;

    @RequestMapping(value = "/bid", method = RequestMethod.POST)
    public ResponseEntity addNewBid(@RequestBody String json, HttpSession session) {
        Bid bid = BidJsonParser.jsonToBid(json);
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == bid.getUser().getId()) || (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN))) {
                Lot lot = lotService.getById(bid.getLot().getId());
                if ((lot.getMaxBid() != null && bid.getValue() < lot.getMaxBid().getValue() + lot.getMinStep()) || (lot.getMaxBid() == null && bid.getValue() < lot.getStartPrice())) {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                } else {
                    bidService.add(bid);
                    return new ResponseEntity(HttpStatus.OK);
                }
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
}


