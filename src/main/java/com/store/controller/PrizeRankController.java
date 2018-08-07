package com.store.controller;

import com.store.domain.Prize;
import com.store.domain.Rank;
import com.store.service.PrizeService;
import com.store.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("prizes")
public class PrizeRankController {
    @Autowired
    PrizeService prizeService;

    @Autowired
    RankService rankService;

    @RequestMapping(value = "/ranks", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Rank>> getAllRanks(){
        return new ResponseEntity<Iterable<Rank>>(rankService.getAllRank(), HttpStatus.OK);
    }


}
