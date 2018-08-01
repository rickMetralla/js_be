package com.store.controller;

import com.store.LoggerWrapper;
import com.store.domain.Winners;
import com.store.service.WinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class WinnerController {

    @Autowired
    WinnerService winService;
    Logger LOGGER = LoggerWrapper.getInstance().logger;

    @RequestMapping(value = "/winners", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Winners>> getWinners(){
        Iterable<Winners> winners = winService.findAll();
        return new ResponseEntity<Iterable<Winners>>(winners, HttpStatus.OK);
    }

    @RequestMapping(value = "/winners", method = RequestMethod.POST)
    public ResponseEntity<String> createPurchase(@RequestBody Winners winner){
        winService.create(winner);
        return new ResponseEntity<String>("successfully created", HttpStatus.CREATED);
    }
}
