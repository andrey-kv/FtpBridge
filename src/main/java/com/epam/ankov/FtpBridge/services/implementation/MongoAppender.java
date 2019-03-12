package com.epam.ankov.FtpBridge.services.implementation;

import com.epam.ankov.FtpBridge.models.FlightRecord;
import com.epam.ankov.FtpBridge.repositories.FlightRecordsRepository;
import com.epam.ankov.FtpBridge.services.DataAppender;
import com.epam.ankov.FtpBridge.services.FtpReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MongoAppender implements DataAppender {

    private static final Logger log = LoggerFactory.getLogger(MongoAppender.class);

    @Autowired
    private FlightRecordsRepository repository;

    @Override
    public void append(List<String> list) {
        log.info("Append list");
        for (String record : list) {
            FlightRecord fr = new FlightRecord(record);
            if (isRecordNotExist(fr)) {
                log.info("Append new record: " + fr.getFirstName() + " " + fr.getSecondName());
                repository.insert(fr);
            }
        }
    }

    private boolean isRecordNotExist(FlightRecord fr) {
        return !repository.findOne(Example.of(fr)).isPresent();
    }

}
