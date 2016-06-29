package com.koushik.controller;

import java.util.Set;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.koushik.entity.SeatHold;
import com.koushik.entity.SeatHoldContext;
import com.koushik.service.SeatService;
import com.koushik.service.TicketService;
import com.koushik.util.Validator;

@RestController
@EnableAsync
@EnableScheduling
public class ServiceController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SeatService seatService;

    @RequestMapping(value = "/availableSeats", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int availableSeats(@RequestBody(required = false) Set<Integer> venueLevels) {
        return ticketService.getAvailableSeats(venueLevels);
    }

    @RequestMapping(value = "/findAndHoldSeats", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Future<SeatHold> findAndHoldSeats(@RequestBody SeatHoldContext seatHoldContext) {

        if (seatHoldContext != null && seatHoldContext.getNoOfSeats() > 0
                && (Validator.isCollectionNotEmpty(seatHoldContext.getMinLevels())
                || Validator.isCollectionNotEmpty(seatHoldContext.getMaxLevels()))
                && Validator.isNotNull(seatHoldContext.getCustomerEmail())) {

            SeatHold seatHolded = ticketService.findAndHoldSeats(seatHoldContext.getNoOfSeats(),
                    seatHoldContext.getMinLevels(), seatHoldContext.getMaxLevels(), seatHoldContext.getCustomerEmail());
            return new AsyncResult<SeatHold>(seatHolded);
        }
        return null;
    }

    @RequestMapping(value = "/reserveSeats", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Future<String> reserveSeats(@RequestBody SeatHold seatHold) {

        if (seatHold.getId() > 0 && Validator.isNotNull(seatHold.getCustomerEmail())) {
            String reserved = ticketService.reserveSeats((int) seatHold.getId(), seatHold.getCustomerEmail());
            return new AsyncResult<String>(String.valueOf(reserved));
        }
        return null;
    }

    @Scheduled(fixedRateString = "60000")
    public void removeSeatHold() {
        seatService.removeExpiredSeatHolds();
    }
}
