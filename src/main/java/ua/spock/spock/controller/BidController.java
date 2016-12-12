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
import ua.spock.spock.service.BidService;
import ua.spock.spock.service.LotService;
import ua.spock.spock.utils.BidJsonParser;
@Controller
public class BidController {
    @Autowired
    private BidService bidService;
    @Autowired
    private LotService lotService;

    @RequestMapping(value = "/bid", method = RequestMethod.POST)
    public ResponseEntity addNewBid(@RequestBody String json) {
        Bid bid = BidJsonParser.jsonToBid(json);
       Lot lot = lotService.getById(bid.getLot().getId());
        if(bid.getValue()<lot.getMaxBid().getValue()+lot.getMinStep()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else{
       int bidId= bidService.add(bid);
        lotService.updateMaxBidId(bid.getLot().getId(),bidId);
        return new ResponseEntity(HttpStatus.OK);}
    }
}


