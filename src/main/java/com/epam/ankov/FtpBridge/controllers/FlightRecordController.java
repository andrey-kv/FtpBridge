package com.epam.ankov.FtpBridge.controllers;

import com.epam.ankov.FtpBridge.models.FlightNumber;
import com.epam.ankov.FtpBridge.models.FlightRecord;
import com.epam.ankov.FtpBridge.models.JsonResponse;
import com.epam.ankov.FtpBridge.repositories.FlightRecordsRepository;
import com.epam.ankov.FtpBridge.repositories.FlightsRepository;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/flightrecords")
public class FlightRecordController {

    private static final Logger log = LoggerFactory.getLogger(FlightRecordController.class);

    @Autowired
    private FlightRecordsRepository flightRecordsRepository;

    @Autowired
    private FlightsRepository usersRepository;

    @GetMapping
    public List<FlightRecord> getAll() {
        return flightRecordsRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<JsonResponse> addFlight(@RequestBody FlightRecord flightRecord) {

        UpdateResult usersResult = usersRepository.appendUser(flightRecord.getFirstName(), flightRecord.getSecondName());
        UpdateResult numberResult = usersRepository.appendFlightNumber(flightRecord.getFlightNumber());

        log.info("Posted: " + flightRecord.toString());
        return new ResponseEntity<>(new JsonResponse("The record has been inserted"), HttpStatus.OK);
    }
}
