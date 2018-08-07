package com.store.controller;

import com.store.LoggerWrapper;
import com.store.domain.Prize;
import com.store.domain.Rank;
import com.store.service.PrizeService;
import com.store.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("prizes")
public class PrizeRankController {

    @Autowired
    PrizeService prizeService;

    @Autowired
    RankService rankService;

    Logger LOGGER = LoggerWrapper.getInstance().logger;

    @RequestMapping(value = "/ranks", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Rank>> getAllRanks(){
        return new ResponseEntity<Iterable<Rank>>(rankService.getAllRank(), HttpStatus.OK);
    }

    @RequestMapping(value = "/ranks", method = RequestMethod.POST)
    public ResponseEntity<Rank> saveRankPost(@RequestBody Rank rank){
//        validateRankField(rank);
        return new ResponseEntity<Rank>(rankService.create(rank), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Prize>> getAllPrizes(){
        return new ResponseEntity<Iterable<Prize>>(prizeService.getAllPrizes(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Prize> savePrize(@RequestBody Prize prize){
//        validatePrizeFields(prize);
        return new ResponseEntity<Prize>(prizeService.create(prize), HttpStatus.OK);
    }

}
