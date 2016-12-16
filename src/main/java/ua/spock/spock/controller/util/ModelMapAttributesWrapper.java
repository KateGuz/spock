package ua.spock.spock.controller.util;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class ModelMapAttributesWrapper {
    public void fillLotAtributes(ModelMap model, LotDetails lotDetails) {
        model.addAttribute("lots", lotDetails.getActualLots());
        model.addAttribute("timeLeft", lotDetails.getTimeLeft());
        model.addAttribute("isStarted", lotDetails.getIsStarted());
        model.addAttribute("isNotFinished", lotDetails.getIsNotFinished());
        model.addAttribute("bidCount", lotDetails.getBidCount());
        model.addAttribute("currentPrice", lotDetails.getCurrentPrice());
    }
}
